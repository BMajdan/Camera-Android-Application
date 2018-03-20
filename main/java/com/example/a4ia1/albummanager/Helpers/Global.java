package com.example.a4ia1.albummanager.Helpers;

import android.widget.ImageView;

/**
 * Created by 4ia1 on 2017-11-09.
 */
public class Global {
    private static ImageView imageView;

    public static ImageView getImageView() {
        return imageView;
    }

    public static Global setImageView(ImageView imageView) {
        Global.imageView = imageView;
        return null;
    }
}
