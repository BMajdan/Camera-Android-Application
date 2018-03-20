package com.example.a4ia1.albummanager.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.a4ia1.albummanager.R;

import java.io.File;
import java.util.ArrayList;

public class photo extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> ar = new ArrayList<>();
    private RelativeLayout addFolderLayout;
    private EditText inputCreateFolderName;

    protected void createFolderList(){
        listView = (ListView)findViewById(R.id.ListViewItem);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                photo.this, R.layout.list_view_layout, R.id.listAlbumItemText, ar
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(photo.this,folderInfo.class);
                intent.putStringArrayListExtra("folderar", ar);
                intent.putExtra("folderkey", i);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        ar = bundle.getStringArrayList("folderArray");

        createFolderList();

        addFolderLayout = (RelativeLayout)findViewById(R.id.addFolderButton);

        addFolderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(photo.this);
                alert.setTitle("Tworzenie nowego folderu!");
                alert.setMessage("Nazwa folderu:");
                inputCreateFolderName = new EditText(photo.this);
                alert.setView(inputCreateFolderName);

                alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                        File[] files = pic.listFiles();
                        for(int i = 0; i < files.length; i++){
                            if(files[i].getName().toString().equals("Majdan")){
                                File dir = new File(files[i], inputCreateFolderName.getText().toString() );
                                dir.mkdir();
                                ar.add(inputCreateFolderName.getText().toString());
                                createFolderList();
                            }
                        }
                    }

                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
            }
        });

    }
}
