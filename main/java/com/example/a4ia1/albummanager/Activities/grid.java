package com.example.a4ia1.albummanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.a4ia1.albummanager.Helpers.ImageData;
import com.example.a4ia1.albummanager.R;

import java.io.Serializable;
import java.util.ArrayList;

public class grid extends AppCompatActivity {

    RelativeLayout gridOne, gridTwo, gridThree, gridFour;
    ArrayList <ImageData> imageDataArray = new ArrayList<ImageData>();
    int mWidth, mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        getSupportActionBar().hide();

        mWidth= getWindowManager().getDefaultDisplay().getWidth();
        mHeight= getWindowManager().getDefaultDisplay().getHeight();

        gridOne = (RelativeLayout)findViewById(R.id.gridOne);
        gridTwo = (RelativeLayout)findViewById(R.id.gridTwo);
        gridThree = (RelativeLayout)findViewById(R.id.gridThree);
        gridFour = (RelativeLayout)findViewById(R.id.gridFour);

        gridOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDataArray.clear();
                imageDataArray.add(new ImageData(0, 0, mWidth, mHeight/2));
                imageDataArray.add(new ImageData(0, mHeight/2, mWidth, mHeight/2));

                Intent intent = new Intent(grid.this,gridpreview.class);
                intent.putExtra("imageDataArray", imageDataArray);
                startActivity(intent);
            }
        });

        gridTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDataArray.clear();
                imageDataArray.add(new ImageData(0, 0, mWidth/2, mHeight));
                imageDataArray.add(new ImageData(mWidth/2, 0, mWidth/2, mHeight/3));
                imageDataArray.add(new ImageData(mWidth/2, mHeight/3, mWidth/2, mHeight/3));
                imageDataArray.add(new ImageData(mWidth/2, 2 * mHeight/3, mWidth/2, mHeight/3));
                imageDataArray.add(new ImageData(mWidth/2, 3 * mHeight/3, mWidth/2, mHeight/3));

                Intent intent = new Intent(grid.this,gridpreview.class);
                intent.putExtra("imageDataArray", imageDataArray);
                startActivity(intent);
            }
        });

        gridThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDataArray.clear();
                imageDataArray.add(new ImageData(0, 0, mWidth/2, mHeight/4));
                imageDataArray.add(new ImageData(mWidth/2, 0, mWidth/2, mHeight/4));
                imageDataArray.add(new ImageData(0, mHeight/4, mWidth/2, mHeight/4));
                imageDataArray.add(new ImageData(mWidth/2, mHeight/4, mWidth/2, mHeight/4));
                imageDataArray.add(new ImageData(0, 2 * mHeight/4, mWidth/2, mHeight/4));
                imageDataArray.add(new ImageData(mWidth/2, 2 * mHeight/4, mWidth/2, mHeight/4));
                imageDataArray.add(new ImageData(0, 3 * mHeight/4, mWidth/2, mHeight/4));
                imageDataArray.add(new ImageData(mWidth/2, 3 * mHeight/4, mWidth/2, mHeight/4));

                Intent intent = new Intent(grid.this,gridpreview.class);
                intent.putExtra("imageDataArray", imageDataArray);
                startActivity(intent);
            }
        });

        gridFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDataArray.clear();
                imageDataArray.add(new ImageData(0, 0, mWidth, mHeight/3));
                imageDataArray.add(new ImageData(0, mHeight/3, mWidth/2, mHeight/3));
                imageDataArray.add(new ImageData(mWidth/2, mHeight/3, mWidth/2, mHeight/3));
                imageDataArray.add(new ImageData(0, 2 * mHeight/3, mWidth, mHeight/3));

                Intent intent = new Intent(grid.this,gridpreview.class);
                intent.putExtra("imageDataArray", imageDataArray);
                startActivity(intent);
            }
        });
    }
}
