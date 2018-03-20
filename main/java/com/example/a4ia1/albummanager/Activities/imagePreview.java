package com.example.a4ia1.albummanager.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.a4ia1.albummanager.Components.DrawerArrayAdapter;
import com.example.a4ia1.albummanager.Components.MyFTPClientFunctions;
import com.example.a4ia1.albummanager.Helpers.DrawerList;
import com.example.a4ia1.albummanager.Helpers.LetterBorder;
import com.example.a4ia1.albummanager.Helpers.TextCanvas;
import com.example.a4ia1.albummanager.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class imagePreview extends AppCompatActivity {

    private ImageView previewImage;
    private RelativeLayout imgPrevText;
    private RelativeLayout deleteButton;
    private MyFTPClientFunctions ftpclient = null;
    private ArrayList<String> ar = new ArrayList<>();
    private int folderkey;
    private ProgressDialog pd;

    private ListView drawerListView;
    ArrayList<DrawerList> drawerLists = new ArrayList<DrawerList>();

    private Bitmap betterImageDecode(String filePath) {

        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        myBitmap = BitmapFactory.decodeFile(filePath, options);

        return myBitmap;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        final String pathImage = bundle.getString("imagePath");
        ar = bundle.getStringArrayList("folderar");
        folderkey = bundle.getInt("folderkey");

        previewImage = (ImageView)findViewById(R.id.previewImage);
        final Bitmap myBitmap = betterImageDecode(pathImage);
        previewImage.setImageBitmap(myBitmap);

        imgPrevText = (RelativeLayout)findViewById(R.id.previewLayout);

        deleteButton = (RelativeLayout)findViewById(R.id.deleteButtonLayout);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(imagePreview.this);
                alert.setTitle("Usuwanie zdjęcia");
                alert.setMessage("Czy chcesz usunąc to zdjęcie?");

                alert.setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File myDir = new File(pathImage);
                        myDir.delete();
                        Intent intent = new Intent(imagePreview.this,folderInfo.class);
                        intent.putStringArrayListExtra("folderar", ar);
                        intent.putExtra("folderkey", folderkey);
                        startActivity(intent);
                    }

                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
            }
        });

        drawerLists.add(new DrawerList(0, "Fonts", R.drawable.font));
        drawerLists.add(new DrawerList(1, "Upload", R.drawable.upload));
        drawerLists.add(new DrawerList(2, "Share", R.drawable.share));

        drawerListView = (ListView)findViewById(R.id.drawerListView);

        DrawerArrayAdapter adapter = new DrawerArrayAdapter(
                imagePreview.this,
                R.layout.drawerlistview,
                drawerLists
        );
        drawerListView.setAdapter(adapter);

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(imagePreview.this, letters.class);
                        startActivityForResult(intent, 300);
                        break;
                    case 1:
                        if(isOnline()){

                            ftpclient = new MyFTPClientFunctions();
                            new Thread(new Runnable() {
                                public void run() {
                                    boolean status = false;
                                    status = ftpclient.ftpConnect("serwer1723974.home.pl", "bartek@serwer1723974.home.pl", "ZAK9$AyDp", 21);
                                    if (status == true) {
                                        Bitmap icon;
                                        Bitmap normal = myBitmap;
                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("image/jpeg");
                                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                        ByteArrayOutputStream bytesNormal = new ByteArrayOutputStream();

                                        icon = Bitmap.createScaledBitmap(myBitmap, 100, 100, true);
                                        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                                        normal.compress(Bitmap.CompressFormat.JPEG, 100, bytesNormal);
                                        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
                                        try {
                                            f.createNewFile();
                                            FileOutputStream fo = new FileOutputStream(f);
                                            fo.write(bytes.toByteArray());
                                            SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                                            String d = dFormat.format(new Date());
                                            ftpclient.ftpUpload(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg", "uploadImageMin_" + d + ".jpg", "androidProject", imagePreview.this);

                                            f.createNewFile();
                                            fo = new FileOutputStream(f);
                                            fo.write(bytesNormal.toByteArray());
                                            ftpclient.ftpUpload(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg", "uploadImage_" + d + ".jpg", "androidProject", imagePreview.this);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        Log.d("Connect", "Connection Success");
                                    } else {
                                        Log.d("Connect", "Connection failed");
                                    }
                                }
                            }).start();

                            new Thread(new Runnable() {
                                public void run() {
                                    ftpclient.ftpDisconnect();
                                }
                            }).start();

                        }else{
                            Toast.makeText(imagePreview.this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if(isOnline()){

                            Bitmap icon = myBitmap;
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image/jpeg");
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
                            try {
                                f.createNewFile();
                                FileOutputStream fo = new FileOutputStream(f);
                                fo.write(bytes.toByteArray());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
                            startActivity(Intent.createChooser(share, "Share Image"));
                        }else{
                            Toast.makeText(imagePreview.this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 300){
            Bundle extras = data.getExtras();
            String src = (String) extras.get("src");
            String text = (String) extras.get("text");
            int color = (int) extras.get("color");
            int fill = (int)extras.get("fill");

            Typeface tf = Typeface.createFromAsset(getAssets(),src);

            TextCanvas newTextPreview = new TextCanvas(imagePreview.this, text, tf, color, fill);
            newTextPreview.setX(100);
            newTextPreview.setY(300);
            newTextPreview.setLayoutParams(new RelativeLayout.LayoutParams((int)newTextPreview.getTextWidth(), (int)newTextPreview.getTextHeight()));
            final LetterBorder border = new LetterBorder(imagePreview.this, (int)newTextPreview.getTextWidth() + 10, (int)newTextPreview.getTextHeight() + 10);
            border.setLayoutParams(new RelativeLayout.LayoutParams((int)newTextPreview.getTextWidth() + 10, (int)newTextPreview.getTextWidth() + 10));
            newTextPreview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    final int X = (int) event.getRawX();
                    final int Y = (int) event.getRawY();
                    TextCanvas tmp = (TextCanvas)view;

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            border.setX(tmp.getX() - 5);
                            border.setY(tmp.getY() - 5);
                            imgPrevText.addView(border);
                            break;
                        case MotionEvent.ACTION_UP:
                            imgPrevText.removeView(border);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            tmp.setX(X - tmp.getTextWidth()/2f);
                            tmp.setY(Y - tmp.getTextWidth()/2f);

                            border.setX(tmp.getX());
                            border.setY(tmp.getY());
                            break;
                    }
                    return true;
                }
            });

            imgPrevText.addView(newTextPreview);
        }
    }
}
