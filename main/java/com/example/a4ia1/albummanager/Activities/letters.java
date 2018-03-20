package com.example.a4ia1.albummanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.Image;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a4ia1.albummanager.Helpers.Miniatura;
import com.example.a4ia1.albummanager.Helpers.TextCanvas;
import com.example.a4ia1.albummanager.R;

import java.io.IOException;

public class letters extends AppCompatActivity {

    private RelativeLayout lettersTextPreview, saveLetters, colorPicker, colorPickerBig, bigColorPicker, colorPickerFill;
    private ImageView colorImageViewBig;
    private EditText lettersEditText;
    private LinearLayout lettersScrollView;
    private Typeface tf;
    private Typeface newtf;
    private String[] lista;
    private String src;
    private TextCanvas newTextPreview;
    private int color = Color.BLACK;
    private int fill = Color.RED;
    private boolean checkFill = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters);
        getSupportActionBar().hide();

        lettersTextPreview = (RelativeLayout) findViewById(R.id.lettersTextPreview);
        lettersEditText = (EditText) findViewById(R.id.lettersEditText);
        lettersScrollView = (LinearLayout) findViewById(R.id.lettersScrollView);
        saveLetters = (RelativeLayout)findViewById(R.id.saveLetters);

        colorPicker = (RelativeLayout)findViewById(R.id.turnColorPicker);
        colorPickerFill = (RelativeLayout)findViewById(R.id.turnColorPickerFill);

        bigColorPicker = (RelativeLayout)findViewById(R.id.bigColorPicker);
        colorImageViewBig = (ImageView)findViewById(R.id.colorImageViewBig);
        colorImageViewBig.setDrawingCacheEnabled(true);

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigColorPicker.setVisibility(View.VISIBLE);
                checkFill = false;
            }
        });

        colorPickerFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigColorPicker.setVisibility(View.VISIBLE);
                checkFill = true;
            }
        });

        colorImageViewBig.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                ImageView temp = (ImageView)view;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Bitmap bmp = temp.getDrawingCache();
                        if(checkFill){
                            fill = bmp.getPixel((int)event.getX(), (int)event.getY());
                            Log.d("Fill", fill + "");
                        }else{
                            color = bmp.getPixel((int)event.getX(), (int)event.getY());
                            Log.d("Color", color + "");
                        }
                        TextCanvas newTextPreview = new TextCanvas(letters.this, lettersEditText.getText().toString(), newtf, color, fill);
                        lettersTextPreview.removeAllViews();
                        lettersTextPreview.addView(newTextPreview);
                        break;
                    case MotionEvent.ACTION_UP:
                        bigColorPicker.setVisibility(View.GONE);
                        break;

                }
                return true;
            }
        });

        AssetManager assetManager = getAssets();
        try {
            lista = assetManager.list("fonts");

            for(int i = 0; i < lista.length; i++){
                TextView newText = new TextView(letters.this);
                newText.setText("Zażółć gęślą jaźń");
                RelativeLayout newRelativ = new RelativeLayout(letters.this);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        8.0f
                );
                newRelativ.setLayoutParams(param);
                newRelativ.setPadding(10, 10, 10, 10);
                newRelativ.setGravity(Gravity.CENTER);
                newRelativ.addView(newText);
                newText.setX(60);
                newText.setY(120);

                newRelativ.setId(i);
                newText.setTextSize(35);
                tf = Typeface.createFromAsset(getAssets(),"fonts/" + lista[i]);
                newtf = Typeface.createFromAsset(getAssets(),"fonts/" + lista[i]);
                src = "fonts/" + lista[i];
                newText.setTypeface (tf);
                lettersScrollView.addView(newRelativ);

                newRelativ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout tmp = (RelativeLayout)v;
                        newtf = Typeface.createFromAsset(getAssets(),"fonts/" + lista[tmp.getId()]);
                        src = "fonts/" + lista[tmp.getId()];
                        newTextPreview = new TextCanvas(letters.this, lettersEditText.getText().toString(), newtf, color, fill);
                        lettersTextPreview.removeAllViews();
                        lettersTextPreview.addView(newTextPreview);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        TextWatcher textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TextCanvas newTextPreview = new TextCanvas(letters.this, lettersEditText.getText().toString(), newtf, color, fill);
                lettersTextPreview.removeAllViews();
                lettersTextPreview.addView(newTextPreview);
            }
        };
        lettersEditText.addTextChangedListener(textWatcher);

        saveLetters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(letters.this, imagePreview.class);
                intent.putExtra("src", src);
                intent.putExtra("text", lettersEditText.getText().toString());
                intent.putExtra("color", color);
                intent.putExtra("fill", fill);
                setResult(300, intent);
                finish();
            }
        });
    }
}
