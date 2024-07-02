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

import com.example.codigomorse.entity.EnableLetterItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameActivity extends AppCompatActivity {

    private String answer = "";
    private String question = "";
    private String codeType;
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

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("codeType"))
            codeType = intent.getStringExtra("codeType");
        else
            codeType = "Morse";

        createCodeTypeMap();
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
                            }
                        }
                    }
                }
        );

        setNewQuestion();
    }

    public void createCodeTypeMap(){
        if(codeType.equals("Morse")){
            codeMap = new HashMap<>();
            codeMap.put('A', ".-");
            codeMap.put('B', "-...");
            codeMap.put('C', "-.-.");
            codeMap.put('D', "-..");
            codeMap.put('E', ".");
            codeMap.put('F', "..-.");
            codeMap.put('G', "--.");
            codeMap.put('H', "....");
            codeMap.put('I', "..");
            codeMap.put('J', ".---");
            codeMap.put('K', "-.-");
            codeMap.put('L', ".-..");
            codeMap.put('M', "--");
            codeMap.put('N', "-.");
            codeMap.put('O', "---");
            codeMap.put('P', ".--.");
            codeMap.put('Q', "--.-");
            codeMap.put('R', ".-.");
            codeMap.put('S', "...");
            codeMap.put('T', "-");
            codeMap.put('U', "..-");
            codeMap.put('V', "...-");
            codeMap.put('W', ".--");
            codeMap.put('X', "-..-");
            codeMap.put('Y', "-.--");
            codeMap.put('Z', "--..");
        } else if(codeType.equals("Binary")){
            codeMap.put('A', "01000001");
            codeMap.put('B', "01000010");
            codeMap.put('C', "01000011");
            codeMap.put('D', "01000100");
            codeMap.put('E', "01000101");
            codeMap.put('F', "01000110");
            codeMap.put('G', "01000111");
            codeMap.put('H', "01001000");
            codeMap.put('I', "01001001");
            codeMap.put('J', "01001010");
            codeMap.put('K', "01001011");
            codeMap.put('L', "01001100");
            codeMap.put('M', "01001101");
            codeMap.put('N', "01001110");
            codeMap.put('O', "01001111");
            codeMap.put('P', "01010000");
            codeMap.put('Q', "01010001");
            codeMap.put('R', "01010010");
            codeMap.put('S', "01010011");
            codeMap.put('T', "01010100");
            codeMap.put('U', "01010101");
            codeMap.put('V', "01010110");
            codeMap.put('W', "01010111");
            codeMap.put('X', "01011000");
            codeMap.put('Y', "01011001");
            codeMap.put('Z', "01011010");
            codeMap.put('0', "00110000");
            codeMap.put('1', "00110001");
            codeMap.put('2', "00110010");
            codeMap.put('3', "00110011");
            codeMap.put('4', "00110100");
            codeMap.put('5', "00110101");
            codeMap.put('6', "00110110");
            codeMap.put('7', "00110111");
            codeMap.put('8', "00111000");
            codeMap.put('9', "00111001");
        } else if(codeType.equals("Tap Code")){
            codeMap.put('A', ". .");
            codeMap.put('B', ". ..");
            codeMap.put('C', ". ...");
            //codeMap.put('K', "13");
            codeMap.put('D', ". ....");
            codeMap.put('E', ". .....");
            codeMap.put('F', ".. .");
            codeMap.put('G', ".. ..");
            codeMap.put('H', ".. ...");
            codeMap.put('I', ".. ....");
            codeMap.put('J', ".. .....");
            codeMap.put('L', "... .");
            codeMap.put('M', "... ..");
            codeMap.put('N', "... ...");
            codeMap.put('O', "... ....");
            codeMap.put('P', "... .....");
            codeMap.put('Q', ".... .");
            codeMap.put('R', ".... ..");
            codeMap.put('S', ".... ...");
            codeMap.put('T', ".... ....");
            codeMap.put('U', ".... .....");
            codeMap.put('V', "..... .");
            codeMap.put('W', "..... ..");
            codeMap.put('X', "..... ...");
            codeMap.put('Y', "..... ....");
            codeMap.put('Z', "..... .....");
        }
    }

    private void createEnabledLettersList() {
        enableLetterItemList.clear();
        codeMap.forEach((key, value) -> enableLetterItemList.add(new EnableLetterItem(null, codeType, key, value, true)));
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

        System.out.println("Question (" + codeType + "): " + question + ", Answer (Letter): " + answer);

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

    public void onSaveScore(View view) {
        Intent intent = new Intent(this, SaveScoreActivity.class);
        intent.putParcelableArrayListExtra("itemList", new ArrayList<>(enableLetterItemList));
        resultLauncher.launch(intent);
    }
}