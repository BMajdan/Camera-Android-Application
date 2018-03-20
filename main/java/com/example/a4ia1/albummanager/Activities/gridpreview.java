package com.example.a4ia1.albummanager.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.a4ia1.albummanager.Helpers.ImageData;
import com.example.a4ia1.albummanager.Helpers.Global;
import com.example.a4ia1.albummanager.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class gridpreview extends AppCompatActivity {

    FrameLayout frame;
    ImageView newImage;
    RelativeLayout saveGrid;
    ImageView g = Global.getImageView();
    private EditText inputCreateFolderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridpreview);
        getSupportActionBar().hide();

        ArrayList<ImageData> imageDataArray = (ArrayList<ImageData>) getIntent().getExtras().getSerializable("imageDataArray");
        frame = (FrameLayout)findViewById(R.id.gridPosition);

        saveGrid = (RelativeLayout)findViewById(R.id.saveGrid);

        for(int i = 0; i < imageDataArray.size(); i++){
            RelativeLayout newLayout = new RelativeLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imageDataArray.get(i).getW() - 2, imageDataArray.get(i).getH() - 2);
            newLayout.setLayoutParams(params);
            newLayout.setX(imageDataArray.get(i).getX() + 1);
            newLayout.setY(imageDataArray.get(i).getY() + 1);
            newLayout.setBackgroundColor(Color.WHITE);
            frame.addView(newLayout);
            float pixels =  48 * gridpreview.this.getResources().getDisplayMetrics().density;
            ImageView newImage = new ImageView(this);
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams((int)pixels, (int)pixels);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            newImage.setLayoutParams(layoutParams);
            newImage.setImageResource(R.drawable.newphoto);
            newLayout.addView(newImage);

            newLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final RelativeLayout temp = (RelativeLayout) v;
                    Global.setImageView((ImageView)temp.getChildAt(0));
                    android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(gridpreview.this);
                    alert.setTitle("Co chcesz zrobić? ");
                    String[] opcje = {"Wybierz z galerii", "Zrób zdjęcie"};
                    alert.setItems(opcje, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, 100);
                                    break;
                                case 1:
                                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(intent, 200);
                                    }
                                    break;
                            }

                        }
                    });
                    alert.show();
                    return false;
                }
            });
        }

        saveGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame.setDrawingCacheEnabled(true);
                final Bitmap b = frame.getDrawingCache(true);

                final AlertDialog.Builder alert = new AlertDialog.Builder(gridpreview.this);
                alert.setTitle("Zapisywanie kolażu");
                alert.setMessage("Czy chcesz zapisać ten kolaż?");
                alert.setMessage("Nazwa zdjęcia:");
                inputCreateFolderName = new EditText(gridpreview.this);
                alert.setView(inputCreateFolderName);
                alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        try {
                            SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String d = dFormat.format(new Date());
                            FileOutputStream fs = new FileOutputStream(pic + "/Majdan/Kolaże/" + inputCreateFolderName.getText().toString() + d + ".jpg");
                            b.compress(Bitmap.CompressFormat.JPEG, 100, fs);
                            fs.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Global.getImageView().setLayoutParams(layoutParams);
            Bundle extras = data.getExtras();
            Bitmap oryginal = (Bitmap) extras.get("data");
            Global.getImageView().setImageBitmap(oryginal);
            Global.getImageView().setCropToPadding(true);
            Global.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else if(requestCode == 100){
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Global.getImageView().setLayoutParams(layoutParams);
            Uri imgData = data.getData();
            InputStream stream = null;
            try {
                stream = getContentResolver().openInputStream(imgData);
                Bitmap oryginal = BitmapFactory.decodeStream(stream);
                Global.getImageView().setImageBitmap(oryginal);
                Global.getImageView().setCropToPadding(true);
                Global.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
