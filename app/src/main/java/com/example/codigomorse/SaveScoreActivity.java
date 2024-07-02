package com.example.codigomorse;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codigomorse.database.ScoreDAO;
import com.example.codigomorse.model.Score;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SaveScoreActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    private int userId;
    private int scorePoints;
    private int languageId;

    private FusedLocationProviderClient fusedLocationClient;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_score);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLastLocation();
        }

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        scorePoints = intent.getIntExtra("scorePoints", -1);
        languageId = intent.getIntExtra("languageId", -1);
        imageView = findViewById(R.id.imageView);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
        String formattedDate = sdf.format(calendar.getTime());

        ((TextView) findViewById(R.id.lblSaveScorePoints)).setText("Score: " + scorePoints);
        ((TextView) findViewById(R.id.lblSaveScoreDate)).setText("Date: " + formattedDate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location){
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    });
        }
    }

    private final ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(imageBitmap);

                        File arquivo = salvarBitmapComoArquivo(imageBitmap);
                        String caminhoArquivo = arquivo.getAbsolutePath();

                        saveScoreToDatabase(caminhoArquivo, "" + scorePoints, userId, languageId);
                    }
                }
            }
    );

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCameraIntent();
        }
    }

    private void startCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            takePictureLauncher.launch(takePictureIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void dispatchTakePictureIntentOld() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            takePictureLauncher.launch(takePictureIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private File salvarBitmapComoArquivo(Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String nomeArquivo = "IMG_" + timeStamp + ".jpg";

        File diretorioArmazenamento = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File arquivoTemporario = new File(diretorioArmazenamento, nomeArquivo);

        try {
            FileOutputStream fos = new FileOutputStream(arquivoTemporario);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arquivoTemporario;
    }

    private void saveScoreToDatabase(String caminhoArquivo, String level, int userId, int languageId) {
        getLastLocation();
        String location = "";

        if(latitude != null && longitude != null){
            location = latitude + ";" + longitude;
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
        String formattedDate = sdf.format(calendar.getTime());

        Score score = new Score(level, userId, caminhoArquivo, location, languageId, formattedDate);
        ScoreDAO scoreDao = new ScoreDAO(this);
        scoreDao.addScore(score);

        Toast.makeText(getApplicationContext(), "Score saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void saveScore(View view) {
        dispatchTakePictureIntent();
    }

    public void onExitSaveScore(View view){
        finish();
    }
}