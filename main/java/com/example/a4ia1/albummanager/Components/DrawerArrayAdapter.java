package com.example.a4ia1.albummanager.Components;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a4ia1.albummanager.Helpers.DrawerList;
import com.example.a4ia1.albummanager.Helpers.Note;
import com.example.a4ia1.albummanager.R;

import java.util.ArrayList;

/**
 * Created by 4ia1 on 2017-11-16.
 */
public class DrawerArrayAdapter extends ArrayAdapter {

    private ArrayList _list;
    private Context _context;
    private int _resource;

    public DrawerArrayAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this._list = objects;
        this._context = context;
        this._resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.drawerlistview, null);
        DrawerList drawer = (DrawerList)_list.get(position);
        TextView tv1 = (TextView) convertView.findViewById(R.id.drawerListName);
        ImageView tv2 = (ImageView) convertView.findViewById(R.id.drawerListImage);

        tv1.setText(drawer.getTitle());
        tv2.setImageResource(drawer.getMiniature());

        return convertView;
    }
}
