package com.example.a4ia1.albummanager.Helpers;

import android.graphics.drawable.Drawable;

/**
 * Created by 4ia1 on 2017-11-16.
 */
public class DrawerList {

    private int id;
    private String title;
    private int miniature;

    public DrawerList(int id, String title, int miniature) {
        this.id = id;
        this.title = title;
        this.miniature = miniature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMiniature() {
        return miniature;
    }

    public void setMiniature(int miniatureName) {
        this.miniature = miniatureName;
    }
}
