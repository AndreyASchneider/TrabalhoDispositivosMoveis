package com.example.codigomorse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.codigomorse.entity.ELI_RecyclerViewAdapter;
import com.example.codigomorse.entity.EnableLetterItem;

import java.util.ArrayList;
import java.util.List;

public class ConfigActivity extends AppCompatActivity {

    List<EnableLetterItem> enableLetterItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("itemList")) {
            enableLetterItemList = intent.getParcelableArrayListExtra("itemList");
        }

        RecyclerView recyclerView = findViewById(R.id.recListLetters);

        ELI_RecyclerViewAdapter adapter = new ELI_RecyclerViewAdapter(this, enableLetterItemList);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void setupLetterList(){
        String[] letters = getResources().getStringArray(R.array.letters_morse);
        String[] translations = getResources().getStringArray(R.array.translations_morse);

        for(int i = 0; i < letters.length; i++){
            enableLetterItemList.add(new EnableLetterItem(null, null, letters[i].charAt(0), translations[i], true));
        }
    }

    public void onEnableLettersConfirm(View view){
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("resultList", new ArrayList<>(enableLetterItemList));
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void onEnableLettersCancel(View view){
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }
}