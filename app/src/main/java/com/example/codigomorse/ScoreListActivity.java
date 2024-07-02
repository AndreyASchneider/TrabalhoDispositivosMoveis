package com.example.codigomorse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codigomorse.database.ScoreDAO;
import com.example.codigomorse.entity.RecyclerViewInterface;
import com.example.codigomorse.entity.Score_RecyclerViewAdapter;
import com.example.codigomorse.model.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreListActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ScoreDAO scoreDAO;

    private int userId;
    private int languageId;

    List<Score> scoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scoreDAO = new ScoreDAO(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId"))
            userId = intent.getIntExtra("userId", -1);

        if (intent != null && intent.hasExtra("languageId"))
            languageId = intent.getIntExtra("languageId", 1);
        else
            languageId = 1;

        setupScoreList();

        RecyclerView recyclerView = findViewById(R.id.recListHighscores);
        Score_RecyclerViewAdapter adapter = new Score_RecyclerViewAdapter(this, scoreList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setupScoreList(){
        List<Score> scoreListDB = scoreDAO.getScoresByUserIdAndLanguageId(userId, languageId);

        List<Score> scoreListOrdered = scoreListDB.stream().filter(x -> x.getPontuation() != null).sorted(Comparator.comparingInt(x -> Integer.parseInt(x.getPontuation()))).collect(Collectors.toList());
        Collections.reverse(scoreListOrdered);

        scoreList.clear();
        scoreList.addAll(scoreListOrdered);
    }

    @Override
    public void onItemClick(int position) {
        Score score = scoreList.get(position);

        Intent intent = new Intent(this, ScoreViewActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    public void onExitScoreListAct(View view){
        finish();
    }
}