package com.example.codigomorse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codigomorse.database.EnabledLetterDAO;
import com.example.codigomorse.database.LanguageDAO;
import com.example.codigomorse.entity.EnableLetterItem;
import com.example.codigomorse.model.EnabledLetter;
import com.example.codigomorse.model.Language;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameActivity extends AppCompatActivity {

    private LanguageDAO languageDAO;
    private EnabledLetterDAO enabledLetterDAO;

    private String answer = "";
    private String question = "";

    private int userId;
    private Language language;
    private Map<Character, String> codeMap = new HashMap<>();
    private List<EnableLetterItem> enableLetterItemList = new ArrayList<>();

    private int difficulty = 1;
    private int level = 0;

    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        languageDAO = new LanguageDAO(this);
        enabledLetterDAO = new EnabledLetterDAO(this);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("userId"))
            userId = intent.getIntExtra("userId", -1);

        int languageId;
        if (intent != null && intent.hasExtra("languageId"))
            languageId = intent.getIntExtra("languageId", 1);
        else
            languageId = 1;

        language = languageDAO.getLanguageById(languageId);

        codeMap = createCodeTypeMap();
        createEnabledLettersList();

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.hasExtra("resultList")) {
                            ArrayList<EnableLetterItem> resultList = data.getParcelableArrayListExtra("resultList");
                            if (resultList != null) {
                                enableLetterItemList.clear();
                                enableLetterItemList.addAll(resultList);
                                updateEnabledLetters();
                            }
                        }
                    }
                }
        );

        onReset(null);

        setNewQuestion();
    }

    public Map<Character, String> createCodeTypeMap(){
        return createTranslationMap(language.getLetters(), language.getTranslations());
    }

    public static Map<Character, String> createTranslationMap(String letters, String translations) {
        Map<Character, String> translationMap = new HashMap<>();

        String[] letterArray = letters.split(";");
        String[] translationArray = translations.split(";");

        if (letterArray.length != translationArray.length) {
            throw new IllegalArgumentException("The number of letters and translations must be the same.");
        }

        for (int i = 0; i < letterArray.length; i++) {
            if (letterArray[i].length() != 1) {
                throw new IllegalArgumentException("Each entry in the letters string should be a single character.");
            }
            translationMap.put(letterArray[i].charAt(0), translationArray[i]);
        }

        return translationMap;
    }

    private void createEnabledLettersList() {
        enableLetterItemList.clear();
        codeMap.forEach((key, value) -> enableLetterItemList.add(new EnableLetterItem(key, value, false)));

        EnabledLetter enabledLetter = enabledLetterDAO.getEnabledLetterByUserIdAndLanguageId(userId, language.getId());

        String[] letterArray = enabledLetter.getLetters().split(";");

        for (int i = 0; i < letterArray.length; i++) {
            if (letterArray[i].length() != 1) {
                throw new IllegalArgumentException("Each entry in the letters string should be a single character.");
            }

            int index = i;
            enableLetterItemList.stream().filter(x -> x.getLetter().equals(letterArray[index].charAt(0))).findFirst().ifPresent(item -> item.setEnabled(true));
        }
    }

    public void updateEnabledLetters(){
        String concatLetters = enableLetterItemList.stream().filter(EnableLetterItem::isEnabled).map(x -> x.getLetter().toString()).collect(Collectors.joining(";"));

        EnabledLetter enabledLetterUpdated = new EnabledLetter(userId, language.getId(), concatLetters);
        enabledLetterDAO.updateEnabledLetterByUserIdAndLanguageId(enabledLetterUpdated);
    }

    public void onConfirm(View view) {
        EditText inputAnswer = findViewById(R.id.inputAnswer);
        String inputText = inputAnswer.getText().toString().toUpperCase().trim();

        if (inputText.equals(answer)) {
            confirmSuccess();
        } else {
            confirmFailed();
        }
    }

    public void confirmSuccess() {
        level++;
        difficulty = (level - (level % 5)) / 5 + 1;

        if (difficulty > 5) {
            difficulty = 5;
        }

        ((TextView) findViewById(R.id.lblScore)).setText("Level: " + level + " Difficulty: " + difficulty);
        ((TextView) findViewById(R.id.lblSuccess)).setText("Success!");
        ((TextView) findViewById(R.id.lblSuccess)).setTextColor(ContextCompat.getColor(this, R.color.success));
        setNewQuestion();
    }

    public void confirmFailed() {
        ((TextView) findViewById(R.id.lblSuccess)).setText("Failed!");
        ((TextView) findViewById(R.id.lblSuccess)).setTextColor(ContextCompat.getColor(this, R.color.failed));
    }

    public void renderQuestion() {
        TextView labelQuestion = findViewById(R.id.lblQuestion);
        labelQuestion.setText(question);
    }

    public void setNewQuestion() {
        TextInputEditText inputAnswer = findViewById(R.id.inputAnswer);
        inputAnswer.setText("");

        List<EnableLetterItem> filteredList = enableLetterItemList.stream().filter(EnableLetterItem::isEnabled).collect(Collectors.toList());

        if(filteredList.isEmpty())
            filteredList.add(enableLetterItemList.get(0));

        List<Character> randomLetters = IntStream.range(0, difficulty)
                .mapToObj(i -> filteredList.get(new Random().nextInt(filteredList.size())).getLetter())
                .collect(Collectors.toList());

        answer = randomLetters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        question = randomLetters.stream()
                .map(letter -> codeMap.getOrDefault(letter, ""))
                .collect(Collectors.joining(" "));

        System.out.println("Question (" + language.getName() + "): " + question + ", Answer (Letter): " + answer);

        renderQuestion();
    }

    public void onReset(View view) {
        level = 1;
        difficulty = 1;
        ((TextView) findViewById(R.id.lblScore)).setText("Level: " + level + " Difficulty: " + difficulty);
        setNewQuestion();
        ((TextView) findViewById(R.id.lblSuccess)).setText("");
    }

    public void goToConfig(View view){
        Intent intent = new Intent(this, ConfigActivity.class);
        intent.putParcelableArrayListExtra("itemList", new ArrayList<>(enableLetterItemList));
        resultLauncher.launch(intent);
    }

    public void goToSaveScore(View view) {
        Intent intent = new Intent(this, SaveScoreActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("scorePoints", level);
        intent.putExtra("languageId", language.getId());
        startActivity(intent);
    }

    public void onExitGameAct(View view){
        finish();
    }
}