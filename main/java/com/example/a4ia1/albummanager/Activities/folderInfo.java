package com.example.a4ia1.albummanager.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.a4ia1.albummanager.Components.DatabaseManager;
import com.example.a4ia1.albummanager.R;

import java.io.File;
import java.util.ArrayList;

public class folderInfo extends AppCompatActivity {

    private RelativeLayout deleteButton;
    private ArrayList<String> ar = new ArrayList<>();
    private File[] arrFile;
    private LinearLayout linearscrollLayout;
    private String folderName;
    private int folderkey;
    private DisplayMetrics displayMetrics;
    private int width;
    private int height;
    private int buf;
    private String color;

    private RelativeLayout red, green, blue, purple;

    private Bitmap betterImageDecode(String filePath) {
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }

    protected void Grid(short a, short b, final File[] arrFile, final int k){
        LinearLayout lin = new LinearLayout(folderInfo.this);
        lin.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
        lin.setOrientation(LinearLayout.HORIZONTAL);
        buf = k;
        LinearLayout lin2 = new LinearLayout(folderInfo.this);
        lin2.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height,
                (float)a));
        lin2.setClipToPadding(true);
        lin2.setPadding(0,0,0,0);

        ImageView image = new ImageView(folderInfo.this);
        Bitmap myBitmap = betterImageDecode(arrFile[k].getAbsolutePath());
        image.setImageBitmap(myBitmap);
        image.setCropToPadding(true);
        image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(folderInfo.this,imagePreview.class);
                intent.putExtra("imagePath", (arrFile[buf].getAbsolutePath()).toString());
                intent.putStringArrayListExtra("folderar", ar);
                intent.putExtra("folderkey", folderkey);
                startActivity(intent);
            }
        });

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(folderInfo.this);
                alert.setTitle("Tworzenie notatki!");

                final View view = View.inflate(folderInfo.this, R.layout.notedialog, null);
                alert.setView(view);

                red = (RelativeLayout)view.findViewById(R.id.noteColorRed);
                green = (RelativeLayout)view.findViewById(R.id.noteColorGreen);
                blue = (RelativeLayout)view.findViewById(R.id.noteColorBlue);
                purple = (RelativeLayout)view.findViewById(R.id.noteColorPurple);

                red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        color = "#ff0000";
                    }
                });

                green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        color = "#00ff00";
                    }
                });

                blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        color = "#0000ff";
                    }
                });

                purple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        color = "#FF00FF";
                    }
                });

                alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText title = (EditText)view.findViewById(R.id.noteTitle);
                        EditText notetext = (EditText)view.findViewById(R.id.noteText);
                        insertDatabase(title.getText().toString(), notetext.getText().toString(), color, arrFile[k].getName());
                    }

                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
                return false;
            }
        });

        lin2.addView(image);
        lin.addView(lin2);

        if((k + 1) < arrFile.length) {
            image = new ImageView(folderInfo.this);
            myBitmap = betterImageDecode(arrFile[k + 1].getAbsolutePath());
            image.setImageBitmap(myBitmap);
            image.setCropToPadding(true);
            image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(folderInfo.this,imagePreview.class);
                    intent.putExtra("imagePath", (arrFile[buf + 1].getAbsolutePath()).toString());
                    intent.putStringArrayListExtra("folderar", ar);
                    intent.putExtra("folderkey", folderkey);
                    startActivity(intent);
                }
            });

            image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(folderInfo.this);
                    alert.setTitle("Tworzenie notatki!");

                    View view = View.inflate(folderInfo.this, R.layout.notedialog, null);
                    alert.setView(view);

                    red = (RelativeLayout)findViewById(R.id.noteColorRed);
                    green = (RelativeLayout)findViewById(R.id.noteColorGreen);
                    blue = (RelativeLayout)findViewById(R.id.noteColorBlue);
                    purple = (RelativeLayout)findViewById(R.id.noteColorPurple);

                    red.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            color = "#ff0000";
                        }
                    });

                    green.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            color = "#00ff00";
                        }
                    });

                    blue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            color = "#0000ff";
                        }
                    });

                    purple.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            color = "#FF00FF";
                        }
                    });

                    alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            EditText title = (EditText)findViewById(R.id.noteTitle);
                            EditText notetext = (EditText)findViewById(R.id.noteText);
                            insertDatabase(title.getText().toString(), notetext.getText().toString(), color, arrFile[k].getName());
                        }

                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
                    return false;
                }
            });

            lin2 = new LinearLayout(folderInfo.this);
            lin2.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    height,
                    (float)b));
            lin2.setClipToPadding(true);
            lin2.setPadding(0,0,0,0);

            lin2.addView(image);
            lin.addView(lin2);
        }
        linearscrollLayout.addView(lin);
    }

    private void insertDatabase(String title, String text, String color, String path){

        DatabaseManager db = new DatabaseManager(
                folderInfo.this,
                "NotesBartoszMajdan.db",
                null,
                6
        );

        if(db.insert(title, text, color, path)){
            Toast.makeText(folderInfo.this, "Dodano do bazy danych.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(folderInfo.this, "Blad.", Toast.LENGTH_SHORT).show();
        }

    }

    protected void loadImages(String folderName){
        linearscrollLayout = (LinearLayout)findViewById(R.id.scrollLayoutImages);
        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        File[] files = pic.listFiles();
        for(int i = 0; i < files.length; i++){
            if(files[i].getName().toString().equals("Majdan")){
                File[] dirFilesList = files[i].listFiles();
                for(int j = 0; j < dirFilesList.length; j++) {
                    if (dirFilesList[j].getName().toString().equals(folderName)) {
                        arrFile = dirFilesList[j].listFiles();
                        int count = 0;
                        for(int k = 0; k < arrFile.length; k+=2){
                            if(count%2==0){
                                if(arrFile[k].isFile()){
                                    Grid((short)1, (short)2, arrFile, k);
                                    count++;
                                }
                            }else{
                                if(arrFile[k].isFile()) {
                                    Grid((short) 2, (short) 1, arrFile, k);
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_info);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        ar = bundle.getStringArrayList("folderar");
        folderkey = bundle.getInt("folderkey");
        folderName = ar.get(folderkey).toString();

        displayMetrics = folderInfo.this.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels / 3;
        height = displayMetrics.widthPixels / 3;

        loadImages(folderName);

        deleteButton = (RelativeLayout)findViewById(R.id.deleteButtonLayout);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(folderInfo.this);
                alert.setTitle("Usuwanie folderu");
                alert.setMessage("Czy chcesz usunąc ten folder?");

                alert.setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                        File[] files = pic.listFiles();
                        for(int i = 0; i < files.length; i++){
                            if(files[i].getName().toString().equals("Majdan")){
                                File[] dirFilesList = files[i].listFiles();
                                for(int j = 0; j < dirFilesList.length; j++) {
                                    if (dirFilesList[j].getName().toString().equals(folderName)) {
                                        for (File fileinfilefile : dirFilesList[j].listFiles()) {
                                            fileinfilefile.delete();
                                        }
                                        dirFilesList[j].delete();
                                        ar.remove(folderkey);
                                        Intent intent = new Intent(folderInfo.this,photo.class);
                                        intent.putExtra("folderArray", ar);
                                        startActivity(intent);
                                    }
                                }
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
