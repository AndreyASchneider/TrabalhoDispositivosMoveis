package com.example.codigomorse;

import static android.opengl.ETC1.encodeImage;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codigomorse.database.DatabaseHelper;
import com.example.codigomorse.database.ScoreDAO;
import com.example.codigomorse.entity.EnableLetterItem;
import com.example.codigomorse.model.Score;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SaveScoreActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    private String userId;
    private String level;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_score);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        level = intent.getStringExtra("level");
        language = intent.getStringExtra("language");
        imageView = findViewById(R.id.imageView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);

                File arquivo = salvarBitmapComoArquivo(imageBitmap);
                String caminhoArquivo = arquivo.getAbsolutePath();


                saveScoreToDatabase(caminhoArquivo, level, Integer.parseInt(userId), Integer.parseInt(language));
            }
        }
    }

    private File salvarBitmapComoArquivo(Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String nomeArquivo = "IMG_" + timeStamp + ".jpg";

        // Obtenha o diretório de armazenamento externo do aplicativo
        File diretorioArmazenamento = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Crie um arquivo temporário dentro do diretório de armazenamento
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
        // todo implementar location
        String location = "";
        Score score = new Score(level, userId, caminhoArquivo, location, languageId);
        ScoreDAO scoreDao = new ScoreDAO(this);
        scoreDao.addScore(score);
    }

    public void saveScore(View view) {
        dispatchTakePictureIntent();
    }
}