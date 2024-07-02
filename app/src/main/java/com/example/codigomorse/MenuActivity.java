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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuActivity extends AppCompatActivity {

    private List<String> codeTypes = new ArrayList<>();
    private int codeTypeIndex;
    private String userId;

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

        Intent intent = getIntent();
        userId = intent.getStringExtra("usuarioId");

        setupCodeTypes();

        updateCodeType(0);
    }

    public void setupCodeTypes(){
        codeTypes = new ArrayList<>();
        codeTypes.add("Morse");
        codeTypes.add("Binary");
        codeTypes.add("Tap Code");
    }

    public void onStartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("codeType", codeTypes.get(codeTypeIndex));
        intent.putExtra("user", userId);
        startActivity(intent);
    }

    public void onClickArrowRight(View view){
        if(codeTypeIndex >= codeTypes.size() - 1)
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

        ((TextView) findViewById(R.id.lblCodeType)).setText(codeTypes.get(codeTypeIndex));

        if(codeTypeIndex == 0)
            ((ImageView) findViewById(R.id.imgSelectModeLeft)).setColorFilter(ContextCompat.getColor(this, R.color.blueBlack), android.graphics.PorterDuff.Mode.MULTIPLY);
        else
            ((ImageView) findViewById(R.id.imgSelectModeLeft)).setColorFilter(ContextCompat.getColor(this, R.color.blueBlackFaded), android.graphics.PorterDuff.Mode.MULTIPLY);

        if(codeTypeIndex == codeTypes.size() - 1)
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
        if(codeTypes.get(codeTypeIndex).equals("Binary"))
            viewBackgroundText.setTextSize(20);
        else
            viewBackgroundText.setTextSize(30);
    }

    public Map<Character, String> createCodeTypeMap(){
        Map<Character, String> codeMap = new HashMap<>();

        if(codeTypes.get(codeTypeIndex).equals("Morse")){
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
        } else if(codeTypes.get(codeTypeIndex).equals("Binary")){
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
        } else if(codeTypes.get(codeTypeIndex).equals("Tap Code")){
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

        return codeMap;
    }
}