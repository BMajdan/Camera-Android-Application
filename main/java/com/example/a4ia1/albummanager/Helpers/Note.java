package com.example.a4ia1.albummanager.Helpers;

/**
 * Created by 4ia2 on 2017-10-05.
 */
public class Note {

    private int id;
    private String title;
    private String text;
    private String color;
    private String image;

    public Note(int id, String title, String text, String color, String image) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.color = color;
        this.image = image;
    }

    public int getNoteId() {
        return id;
    }

    public String getNoteTitle() {
        return title;
    }

    public String getNoteText() {
        return text;
    }

    public String getNoteColor() {
        return color;
    }

    public String getNoteImage() {
        return image;
    }

    /*public void setNoteId(int id) {
        this.id = id;
    }

    public void setNoteTitle(String title) {
        this.title = title;
    }

    public void setNoteText(String text) {
        this.text = text;
    }

    public void setNoteColor(String color) {
        this.color = color;
    }

    public void setNoteImage(String image) {
        this.image = image;
    }*/
}
