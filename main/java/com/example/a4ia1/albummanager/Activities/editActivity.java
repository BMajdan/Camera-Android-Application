package com.example.a4ia1.albummanager.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.a4ia1.albummanager.Components.DatabaseManager;
import com.example.a4ia1.albummanager.Helpers.Note;
import com.example.a4ia1.albummanager.R;

import java.util.ArrayList;

public class editActivity extends AppCompatActivity {
    DatabaseManager db;
    ArrayList<Note> notesList = new ArrayList<Note>();
    Note note;

    RelativeLayout red, green, blue, purple, saveNotes, cancelNotes;
    private String color;
    EditText title, notetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().hide();


        Bundle bundle = getIntent().getExtras();
        final int index = bundle.getInt("notePosition");

        db = new DatabaseManager(
                editActivity.this,
                "NotesBartoszMajdan.db",
                null,
                6
        );

        notesList = db.getOne(index);
        note = (Note)notesList.get(0);

        red = (RelativeLayout)findViewById(R.id.editNoteColorRed);
        green = (RelativeLayout)findViewById(R.id.editNoteColorGreen);
        blue = (RelativeLayout)findViewById(R.id.editNoteColorBlue);
        purple = (RelativeLayout)findViewById(R.id.editNoteColorPurple);

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "#ff0000";
                title.setTextColor(Color.parseColor(color));
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "#00ff00";
                title.setTextColor(Color.parseColor(color));
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "#0000ff";
                title.setTextColor(Color.parseColor(color));
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "#FF00FF";
                title.setTextColor(Color.parseColor(color));
            }
        });

        saveNotes = (RelativeLayout)findViewById(R.id.saveNotes);

        title = (EditText)findViewById(R.id.editNoteTitle);
        notetext = (EditText)findViewById(R.id.editNoteText);

        title.setText(note.getNoteTitle());
        title.setTextColor(Color.parseColor(note.getNoteColor()));
        notetext.setText(note.getNoteText());

        saveNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateValues(index, title.getText().toString(), notetext.getText().toString(), color, note.getNoteImage());
                Intent intent = new Intent(editActivity.this, notes.class);
                startActivity(intent);
            }
        });

        cancelNotes = (RelativeLayout)findViewById(R.id.cancelNotes);
        cancelNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editActivity.this, notes.class);
                startActivity(intent);
            }
        });



    }
}
