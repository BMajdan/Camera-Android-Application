package com.example.a4ia1.albummanager.Components;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a4ia1.albummanager.Helpers.Note;
import com.example.a4ia1.albummanager.R;

import java.util.ArrayList;

/**
 * Created by 4ia2 on 2017-10-05.
 */
public class MyArrayAdapter extends ArrayAdapter {

    private ArrayList _list;
    private Context _context;
    private int _resource;

    public MyArrayAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this._list = objects;
        this._context = context;
        this._resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.notes_layout, null);
        Note note = (Note)_list.get(position);
        TextView tv1 = (TextView) convertView.findViewById(R.id.notesItemPosition);
        TextView tv2 = (TextView) convertView.findViewById(R.id.notesItemTitle);
        TextView tv3 = (TextView) convertView.findViewById(R.id.notesItemText);
        tv1.setText(note.getNoteTitle());
        tv2.setText(note.getNoteText());
        tv3.setText(note.getNoteImage());

        tv1.setTextColor(Color.parseColor(note.getNoteColor()));

        return convertView;
    }
}
