package com.example.a4ia1.albummanager.Components;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a4ia1.albummanager.Helpers.Note;

import java.util.ArrayList;

/**
 * Created by 4ia2 on 2017-10-05.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE imagesNotes (notesId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'title' TEXT, 'text' TEXT, 'color' TEXT, 'image' TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS imagesNotes");
        onCreate(db);
    }

    public boolean insert(String title, String text, String color, String image){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("text", text);
        contentValues.put("color", color);
        contentValues.put("image", image);

        db.insertOrThrow("imagesNotes", null, contentValues); // gdy insert się nie powiedzie, będzie błąd
        db.close();
        return true;
    }

    public ArrayList<Note> getAll(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes= new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM imagesNotes" , null);
        while(result.moveToNext()){
            notes.add( new Note(
                    result.getInt(result.getColumnIndex("notesId")),
                    result.getString(result.getColumnIndex("title")),
                    result.getString(result.getColumnIndex("text")),
                    result.getString(result.getColumnIndex("color")),
                    result.getString(result.getColumnIndex("image"))
            ));
        }
        return notes;
    }

    public ArrayList<Note> getOne(int index){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes= new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM imagesNotes WHERE notesId = " + index , null);
        while(result.moveToNext()){
            notes.add( new Note(
                    result.getInt(result.getColumnIndex("notesId")),
                    result.getString(result.getColumnIndex("title")),
                    result.getString(result.getColumnIndex("text")),
                    result.getString(result.getColumnIndex("color")),
                    result.getString(result.getColumnIndex("image"))
            ));
        }
        return notes;
    }

    public int deleteNote(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(
                "imagesNotes",
                "notesId = ?",
                new String[]{Integer.toString(id)});

    }

    public boolean updateValues(int id, String title, String text, String color, String image){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("text", text);
        contentValues.put("color", color);
        contentValues.put("image", image);

        db.update("imagesNotes", contentValues, "notesId = ? ", new String[]{Integer.toString(id)});
        db.close();

        return true;
    }
}
