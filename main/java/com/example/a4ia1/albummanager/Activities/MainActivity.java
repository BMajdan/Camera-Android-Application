package com.example.a4ia1.albummanager.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a4ia1.albummanager.R;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearCamera, linearPhoto, linearVGrid, linearWebsite, linearNotes;
    private File pic;
    private File[] files;
    private ArrayList<String> ar = new ArrayList<>();

    protected void createFoldersToPhotos() {
        pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (pic.exists()) {
            File dir = new File(pic, "Majdan");
            dir.mkdir();

            if (dir.exists()) {
                File dirindir = new File(dir, "Miejsca");
                dirindir.mkdir();
                dirindir = new File(dir, "Osoby");
                dirindir.mkdir();
                dirindir = new File(dir, "Rzeczy");
                dirindir.mkdir();
                dirindir = new File(dir, "Kolaże");
                dirindir.mkdir();
            }

            files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory())
                    ar.add(files[i].getName());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET},
                1
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
            createFoldersToPhotos();
            linearCamera = (LinearLayout) findViewById(R.id.linearCamera);
            linearPhoto = (LinearLayout) findViewById(R.id.linearPhoto);
            linearVGrid = (LinearLayout) findViewById(R.id.linearVGrid);
            linearWebsite = (LinearLayout) findViewById(R.id.linearWebsite);
            linearNotes = (LinearLayout) findViewById(R.id.linearNotes);

            linearCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, camera.class);
                    startActivity(intent);
                }
            });

            linearVGrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, grid.class);
                    startActivity(intent);
                }
            });

            linearWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, website.class);
                    startActivity(intent);
                }
            });

            linearNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, notes.class);
                    startActivity(intent);
                }
            });

            linearPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, photo.class);
                    intent.putExtra("folderArray", ar);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Brak uprawnień do zarządzania pamięcią i aparatem.", Toast.LENGTH_SHORT).show();
        }
        return;
    }
}
