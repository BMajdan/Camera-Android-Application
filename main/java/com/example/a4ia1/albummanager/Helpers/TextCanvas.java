package com.example.a4ia1.albummanager.Helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 4ia1 on 2017-11-16.
 */
public class TextCanvas extends View {

    private String stringText;
    private Typeface tf;
    private int textSize = 300;
    private float textWidth, textHeight;
    private Paint paint, fillPaint;

    public TextCanvas(Context context, String stringText, Typeface tf, int color, int fill) {
        super(context);
        this.stringText=stringText;
        this.tf = tf;

        this.fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.fillPaint.setAntiAlias(true);
        this.fillPaint.setTypeface(tf);
        this.fillPaint.setColor(fill);
        this.fillPaint.setTextSize(textSize);

        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setAntiAlias(true);
        this.paint.setTypeface(tf);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(8);
        this.paint.setColor(color);
        this.paint.setTextSize(textSize);

        Rect bounds = new Rect();
        paint.getTextBounds(stringText, 0, stringText.length(), bounds);
        textWidth = bounds.width() + 10;
        textHeight = bounds.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(stringText,0, textHeight, fillPaint);
        canvas.drawText(stringText,0, textHeight, paint);
    }

    public float getTextWidth() {
        Log.d("Width", textWidth + "");
        return textWidth;
    }

    public float getTextHeight() {
        Log.d("Height", textHeight + "");
        return textHeight;
    }
}
