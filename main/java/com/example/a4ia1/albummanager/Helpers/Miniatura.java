package com.example.a4ia1.albummanager.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Bartek on 2017-11-01.
 */

public class Miniatura extends ImageView {

    private float size;
    private Bitmap bitmap;;

    public Miniatura(Context context, Bitmap bitmap, int size) {
        super(context);
        this.size=size;
        this.bitmap = bitmap;
        this.setImageBitmap(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.argb(150, 255, 255, 255));
        canvas.drawRect(0.0f,0.0f,size,size,paint);
    }

}
