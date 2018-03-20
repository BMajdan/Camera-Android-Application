package com.example.a4ia1.albummanager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a4ia1.albummanager.Components.DatabaseManager;
import com.example.a4ia1.albummanager.Components.MyArrayAdapter;
import com.example.a4ia1.albummanager.Helpers.Note;
import com.example.a4ia1.albummanager.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class notes extends AppCompatActivity {

    private ListView notesListView;
    ArrayList<Note> notesList = new ArrayList<Note>();
    DatabaseManager db;

    private void deleteNote(int position) {
        Note note = (Note)notesList.get(position);
        db.deleteNote(note.getNoteId());
        notesList.remove(position);
        notesListView.invalidateViews();
    }

    private void editNote(int position) {
        Note note = (Note)notesList.get(position);
        Intent intent = new Intent(notes.this, editActivity.class);
        intent.putExtra("notePosition", note.getNoteId());
        startActivity(intent);
    }

    private void sortTitleNote() {
        Collections.sort(notesList, new Comparator<Note>() {
            @Override
            public int compare(Note a, Note b) {
                return a.getNoteText().compareTo(b.getNoteTitle());
            }
        });
        notesListView.invalidateViews();
    }

    private void sortColorNote() {
        Collections.sort(notesList, new Comparator<Note>() {
            @Override
            public int compare(Note a, Note b) {
                return a.getNoteColor().compareTo(b.getNoteColor());
            }
        });
        notesListView.invalidateViews();
    }

    private void sortImageNote() {
        Collections.sort(notesList, new Comparator<Note>() {
            @Override
            public int compare(Note a, Note b) {
                return a.getNoteImage().compareTo(b.getNoteImage());
            }
        });
        notesListView.invalidateViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().hide();

        db = new DatabaseManager(
                notes.this,
                "NotesBartoszMajdan.db",
                null,
                6
        );

        notesList = db.getAll();

        notesListView = (ListView)findViewById(R.id.notesListView);

        MyArrayAdapter adapter = new MyArrayAdapter(
                notes.this,
                R.layout.notes_layout,
                notesList
        );
        notesListView.setAdapter(adapter);

        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(notes.this);
                alert.setTitle("Co chcesz zrobić? ");

                String[] opcje = {"Usuń tą notatkę","Aktualizuj notatkę","Sortuj wg. tytułu", "Sortuj wg. Koloru", "Sortuj wg. Nazwy zdjęcia"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("Which", Integer.toString(which));
                        switch (which){
                            case 0:
                                deleteNote(position);
                                break;
                            case 1:
                                editNote(position);
                                break;
                            case 2:
                                sortTitleNote();
                                break;
                            case 3:
                                sortColorNote();
                                break;
                            case 4:
                                sortImageNote();
                                break;
                        }

                    }
                });
//
                alert.show();
                return false;
            }
        });
    }
}
