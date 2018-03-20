package com.example.a4ia1.albummanager.Helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Bartek on 2017-11-01.
 */

public class Kolo extends View {

    public Context context;
    public Kolo(Context context) {
        super(context);
        this.context = context;
    }

    public int getWid(){
        return getWidth();
    }

    public int getHei(){
        return getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth= getWidth();
        int mHeight= getHeight();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.argb(150, 255, 255, 255));
        canvas.drawCircle(mWidth/2, mHeight/2, mWidth/4, paint);
    }
}
