package com.example.codigomorse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codigomorse.database.LanguageDAO;
import com.example.codigomorse.model.Language;
import com.example.codigomorse.model.Score;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class ScoreViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private LanguageDAO languageDAO;
    private Score score;

    private GoogleMap myMap;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragScoreViewMap);
        mapFragment.getMapAsync(this);

        languageDAO = new LanguageDAO(this);

        score = getIntent().getParcelableExtra("score");

        if(score.getLocation() != null && !score.getLocation().isEmpty()){
            latitude = Double.parseDouble(score.getLocation().split(";")[0]);
            longitude = Double.parseDouble(score.getLocation().split(";")[1]);
        }

        TextView lblScoreViewPoints = findViewById(R.id.lblScoreViewPoints);
        TextView lblScoreViewDate = findViewById(R.id.lblScoreViewDate);
        TextView lblScoreViewCodeType = findViewById(R.id.lblScoreViewCodeType);
        ImageView imgScoreViewPhoto = findViewById(R.id.imgScoreViewPhoto);

        Language language = languageDAO.getLanguageById(score.getLanguageId());

        String langText = language != null ? language.getName() : ("" + score.getLanguageId());

        lblScoreViewPoints.setText("Score: " + score.getPontuation());
        lblScoreViewDate.setText("Date: " + score.getDateCreate());
        lblScoreViewCodeType.setText("Code Type: " + langText);

        String photoPath = score.getFoto();
        if (photoPath != null && !photoPath.isEmpty()) {
            File imgFile = new File(photoPath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgScoreViewPhoto.setImageBitmap(myBitmap);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        if(latitude != null && longitude != null){
            LatLng loc = new LatLng(latitude, longitude);
            myMap.addMarker(new MarkerOptions().position(loc).title("Score location"));
            float zoomLevel = 15.0f;
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoomLevel));
        }
    }

    public void onExitScoreViewAct(View view){
        finish();
    }
}