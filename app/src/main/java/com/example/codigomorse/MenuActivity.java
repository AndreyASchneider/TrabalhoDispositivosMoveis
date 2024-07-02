package com.example.codigomorse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codigomorse.model.User;
import com.example.codigomorse.database.EnabledLetterDAO;
import com.example.codigomorse.database.LanguageDAO;
import com.example.codigomorse.database.UserDAO;
import com.example.codigomorse.model.EnabledLetter;
import com.example.codigomorse.model.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuActivity extends AppCompatActivity {

    private LanguageDAO languageDAO;
    private EnabledLetterDAO enabledLetterDAO;

    private int codeTypeIndex;
    private int userId;

    private List<Language> languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
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

        setupAllDefaultValues();

        loadLanguages();

        updateCodeType(0);

        ((ImageView) findViewById(R.id.imgScoreList)).setColorFilter(ContextCompat.getColor(this, R.color.blueBlackFaded), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void loadLanguages(){
        languages = languageDAO.getAllLanguages();
    }

    public void onStartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("codeType", languages.get(codeTypeIndex).getName());
        intent.putExtra("languageId", languages.get(codeTypeIndex).getId());
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void onClickArrowRight(View view){
        if(codeTypeIndex >= languages.size() - 1)
            return;

        updateCodeType(codeTypeIndex + 1);
    }

    public void onClickArrowLeft(View view){
        if(codeTypeIndex <= 0)
            return;

        updateCodeType(codeTypeIndex - 1);
    }

    public void updateCodeType(int newCodeType){
        codeTypeIndex = newCodeType;

        ((TextView) findViewById(R.id.lblCodeType)).setText(languages.get(codeTypeIndex).getName());

        if(codeTypeIndex == 0)
            ((ImageView) findViewById(R.id.imgSelectModeLeft)).setColorFilter(ContextCompat.getColor(this, R.color.blueBlack), android.graphics.PorterDuff.Mode.MULTIPLY);
        else
            ((ImageView) findViewById(R.id.imgSelectModeLeft)).setColorFilter(ContextCompat.getColor(this, R.color.blueBlackFaded), android.graphics.PorterDuff.Mode.MULTIPLY);

        if(codeTypeIndex == languages.size() - 1)
            ((ImageView) findViewById(R.id.imgSelectModeRight)).setColorFilter(ContextCompat.getColor(this, R.color.blueBlack), android.graphics.PorterDuff.Mode.MULTIPLY);
        else
            ((ImageView) findViewById(R.id.imgSelectModeRight)).setColorFilter(ContextCompat.getColor(this, R.color.blueBlackFaded), android.graphics.PorterDuff.Mode.MULTIPLY);

        updateBackgroundText();
    }

    public void updateBackgroundText(){
        Map<Character, String> codeMap = createCodeTypeMap();

        String source = getResources().getString(R.string.background_text_source).toUpperCase();

        List<Character> charList = source.chars()
                .mapToObj(x -> (char)x)
                .collect(Collectors.toList());

        String encr = charList.stream()
                .map(letter -> codeMap.getOrDefault(letter, ""))
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining(" "));

        TextView viewBackgroundText = findViewById(R.id.lblBackgroundText);
        viewBackgroundText.setText(encr);
        if(languages.get(codeTypeIndex).getName().equals("Binary"))
            viewBackgroundText.setTextSize(20);
        else
            viewBackgroundText.setTextSize(30);
    }

    public Map<Character, String> createCodeTypeMap(){
        Language lang = languages.get(codeTypeIndex);

        Map<Character, String> codeMap = createTranslationMap(lang.getLetters(), lang.getTranslations());

        return codeMap;
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

    public void setupAllDefaultValues(){
        List<Language> defaultLanguages = getDefaultLanguages();

        for(Language defLang : defaultLanguages){
            languageDAO.addLanguageIfNotExists(defLang);
        }

        List<Language> actualLanguages = languageDAO.getAllLanguages();

        for(Language lang : actualLanguages){
            EnabledLetter enabledLetter = new EnabledLetter();
            enabledLetter.setUserId(userId);
            enabledLetter.setLanguageId(lang.getId());
            enabledLetter.setLetters(lang.getLetters());

            enabledLetterDAO.addEnabledLetterIfNotExists(enabledLetter);
        }
    }

    public List<Language> getDefaultLanguages(){
        List<Language> defaultLanguages = new ArrayList<>();

        Language morse = new Language();
        morse.setName("Morse");
        morse.setLetters("A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z");
        morse.setTranslations(".-;-...;-.-.;-..;.;..-.;--.;....;..;.---;-.-;.-..;--;-.;---;.--.;--.-;.-.;...;-;..-;...-;.--;-..-;-.--;--..");
        defaultLanguages.add(morse);

        Language binary = new Language();
        binary.setName("Binary");
        binary.setLetters("A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;0;1;2;3;4;5;6;7;8;9");
        binary.setTranslations("01000001;01000010;01000011;01000100;01000101;01000110;01000111;01001000;01001001;01001010;01001011;01001100;01001101;01001110;01001111;01010000;01010001;01010010;01010011;01010100;01010101;01010110;01010111;01011000;01011001;01011010;00110000;00110001;00110010;00110011;00110100;00110101;00110110;00110111;00111000;00111001");
        defaultLanguages.add(binary);

        Language tapCode = new Language();
        tapCode.setName("Tap Code");
        tapCode.setLetters("A;B;C;D;E;F;G;H;I;J;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z");
        tapCode.setTranslations(". .;. ..;. ...;. ....;. .....;.. .;.. ..;.. ...;.. ....;.. .....;... .;... ..;... ...;... ....;... .....;.... .;.... ..;.... ...;.... ....;.... .....;..... .;..... ..;..... ...;..... ....;..... .....");
        defaultLanguages.add(tapCode);

        Language braille = new Language();
        braille.setName("Braille");
        braille.setLetters("A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z");
        braille.setTranslations("⠁;⠃;⠉;⠙;⠑;⠋;⠛;⠓;⠊;⠚;⠅;⠇;⠍;⠝;⠕;⠏;⠟;⠗;⠎;⠞;⠥;⠧;⠺;⠭;⠽;⠵");
        defaultLanguages.add(braille);

        return defaultLanguages;
    }

    public void onClickImgScoreList(View view){
        openScoreList();
    }

    public void onClickBackgroundText(View view){

    }

    public void openScoreList(){
        Intent intent = new Intent(this, ScoreListActivity.class);
        intent.putExtra("languageId", languages.get(codeTypeIndex).getId());
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}