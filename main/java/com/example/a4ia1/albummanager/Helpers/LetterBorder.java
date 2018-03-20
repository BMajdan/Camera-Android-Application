package com.example.a4ia1.albummanager.Helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Bartek on 2017-12-08.
 */

public class LetterBorder extends View {

    private float width, height;

    public LetterBorder(Context context, float width, float height) {
        super(context);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.argb(150, 255, 255, 255));
        canvas.drawRect(0.0f,0.0f,width,height,paint);
    }
}
