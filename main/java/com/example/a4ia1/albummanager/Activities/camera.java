package com.example.a4ia1.albummanager.Activities;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a4ia1.albummanager.Components.CameraPreview;
import com.example.a4ia1.albummanager.Helpers.Global;
import com.example.a4ia1.albummanager.Helpers.Kolo;
import com.example.a4ia1.albummanager.Helpers.Miniatura;
import com.example.a4ia1.albummanager.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class camera extends AppCompatActivity {

    private Camera camera;
    private int cameraId = -1;
    private CameraPreview cameraPreview;
    private FrameLayout frameLayout;
    private RelativeLayout takePhoto;
    private RelativeLayout savePhoto;
    private File pic;
    private EditText inputCreateFolderName;
    private RelativeLayout colorFilter;
    private RelativeLayout exposure;
    private RelativeLayout bw;
    private RelativeLayout transform;
    private Camera.Parameters camParams;
    Miniatura miniature;
    private byte[] fdata;
    private ArrayList<Miniatura> miniatureArray = new ArrayList<Miniatura>();
    private int miniatureSize = 200;
    private OrientationEventListener orientationEventListener;
    private int rotation;
    float position;
    int minIdent = 0;
    double difference;
    private ArrayList<byte[]> imageByteList= new ArrayList<byte[]>() ;

    private int getCameraId(){
        int cid = 0;
        int camerasCount = Camera.getNumberOfCameras();

        for (int i = 0; i < camerasCount; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);

            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cid = i;
            }
        }

        return cid;
    }

    private void initCamera() {
        boolean cam = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if (!cam) {
            Toast.makeText(camera.this, "Brak Kamery.", Toast.LENGTH_SHORT).show();
        } else {
            cameraId = getCameraId();
            if (cameraId < 0) {
                Toast.makeText(camera.this, "Brak Kamery.", Toast.LENGTH_SHORT).show();
            } else {
                camera = Camera.open(cameraId);
            }
        }
    }

    private void initPreview(){
        cameraPreview = new CameraPreview(camera.this, camera);
        frameLayout = (FrameLayout) findViewById(R.id.cameraLayout);
        frameLayout.addView(cameraPreview);

        Kolo circle = new Kolo(camera.this);
        frameLayout.addView(circle);
    }

    private void showThisPhoto(byte[] fdata){
        final LinearLayout photoPreview = (LinearLayout)findViewById(R.id.imagePreview);
        ImageView previewImageView = (ImageView)findViewById(R.id.imageImage);
        RelativeLayout backButton = (RelativeLayout)findViewById(R.id.backButton);
        Bitmap bitmap = BitmapFactory.decodeByteArray(fdata, 0, fdata.length);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        previewImageView.setImageBitmap(rotatedBitmap);
        previewImageView.setCropToPadding(true);
        previewImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photoPreview.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoPreview.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        getSupportActionBar().hide();
        initCamera();
        initPreview();
        camParams = camera.getParameters();
        takePhoto = (RelativeLayout)findViewById(R.id.takephoto);
        colorFilter = (RelativeLayout)findViewById(R.id.colorFilter);
        exposure = (RelativeLayout)findViewById(R.id.exposure);
        bw = (RelativeLayout)findViewById(R.id.bw);
        transform = (RelativeLayout)findViewById(R.id.transform);
        savePhoto = (RelativeLayout)findViewById(R.id.savePhoto);

        List<Camera.Size> getParams = camParams.getSupportedPictureSizes();
        camParams.setPictureSize(getParams.get(getParams.size() - 1).width, getParams.get(getParams.size() - 1).height);

        orientationEventListener = new OrientationEventListener(camera.this){
            @Override
            public void onOrientationChanged(int angle) {
                int temprotation = 0;
                if(angle<=45 || angle >315) temprotation = 0;
                else if(angle>45 && angle <= 135) temprotation = -90;
                else if(angle>135 && angle <= 225) temprotation = 180;
                else if(angle>225 && angle <= 315) temprotation = 90;
                if (temprotation != rotation) {
                    rotation = temprotation;
                    for(int i=0;i<miniatureArray.size();i++){
                        ObjectAnimator.ofFloat(miniatureArray.get(i), View.ROTATION, rotation)
                                .setDuration(300)
                                .start();
                    }
                    ObjectAnimator.ofFloat(takePhoto, View.ROTATION, rotation)
                            .setDuration(300)
                            .start();
                    ObjectAnimator.ofFloat(colorFilter, View.ROTATION, rotation)
                            .setDuration(300)
                            .start();
                    ObjectAnimator.ofFloat(exposure, View.ROTATION, rotation)
                            .setDuration(300)
                            .start();
                    ObjectAnimator.ofFloat(bw, View.ROTATION, rotation)
                            .setDuration(300)
                            .start();
                    ObjectAnimator.ofFloat(transform, View.ROTATION, rotation)
                            .setDuration(300)
                            .start();

                }

            }
        };
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, camPictureCallback);
            }
            private Camera.PictureCallback camPictureCallback = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, final Camera camera) {
                    fdata = data;
                    imageByteList.add(data);
                    minIdent++;
                    miniatureSize = getWindowManager().getDefaultDisplay().getWidth() / 4;

                    Bitmap bitmap = BitmapFactory.decodeByteArray(fdata, 0, fdata.length);
                    Bitmap smallBmp = Bitmap.createScaledBitmap(bitmap , miniatureSize, miniatureSize, false);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    smallBmp = Bitmap.createBitmap(smallBmp, 0, 0, smallBmp.getWidth(), smallBmp.getHeight(), matrix, true);

                    miniature = new Miniatura(camera.this,smallBmp,miniatureSize);
                    miniature.setId(minIdent);
                    miniature.setLayoutParams(new FrameLayout.LayoutParams(miniatureSize, miniatureSize));
                    miniatureArray.add(miniature);
                    frameLayout.addView(miniature);

                    int mWidth= getWindowManager().getDefaultDisplay().getWidth();
                    int mHeight= getWindowManager().getDefaultDisplay().getHeight();

                    position = 0;
                    if(miniatureArray.size()!=0) {
                        difference = 2*Math.PI/(miniatureArray.size());
                        for(int i=0;i<miniatureArray.size();i++){
                            double x = mWidth/2+ mWidth/4*Math.cos(position);
                            double y = mHeight/2+ mWidth/4*Math.sin(position);
                            position+=difference;
                            miniatureArray.get(i).setX((float)x-miniatureSize/2);
                            miniatureArray.get(i).setY((float)y-miniatureSize);
                        }
                    }

                    miniature.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                                Miniatura temp = (Miniatura)v;
                                switch(event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        temp.setX(event.getRawX() - (float)miniatureSize/2f);
                                        int mWidth= getWindowManager().getDefaultDisplay().getWidth();
                                        if(temp.getX() >= (mWidth - (mWidth/3f))){
                                            frameLayout.removeView(temp);
                                            position -= difference;
                                            imageByteList.remove(miniatureArray.indexOf(temp));
                                            miniatureArray.remove(temp);
                                        }
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        break;
                                }
                            return false;
                        }
                    });

                    miniature.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                                final Miniatura temp = (Miniatura) v;
                                android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(camera.this);
                                alert.setTitle("Co chcesz zrobić? ");
                                String[] opcje = {"Podgląd zdjęcia", "Usuń zdjęcie", "Zapisz zdjęcie"};
                                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                showThisPhoto(fdata);
                                                break;
                                            case 1:
                                                frameLayout.removeView(temp);
                                                position -= difference;
                                                imageByteList.remove(miniatureArray.indexOf(temp));
                                                miniatureArray.remove(temp);
                                                break;
                                            case 2:
                                                savePhotoToFolder(fdata);
                                                break;
                                        }

                                    }
                                });
                                alert.show();
                            return false;
                        }
                    });


                    camera.startPreview();
                    savePhoto.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    List<String> categories = new ArrayList<String>();
                    categories.add("Image Menu:");
                    categories.add("Zapisz ostatnie zdjęcie");
                    categories.add("Zapisz wszystkie zdjęcia");
                    categories.add("Usuń wszystkie zdjęcia");

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(camera.this, android.R.layout.simple_spinner_item, categories);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);

                    spinner.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected( AdapterView<?> parent, View view, int position, long id) {
                                    switch (position){
                                        case 1:
                                            if(fdata != null){
                                                savePhotoToFolder(imageByteList.get(imageByteList.indexOf(fdata)));
                                            }else{
                                                Toast.makeText(camera.this, "Musisz zrobić zdjęcie!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case 2:
                                            for(int i = 0; i < imageByteList.size(); i++){
                                                savePhotoToFolder(imageByteList.get(i));
                                            }
                                            fdata = null;
                                            break;
                                        case 3:
                                            for(int i = 0; i < miniatureArray.size(); i++){
                                                frameLayout.removeView(miniatureArray.get(i));
                                                position -= difference;
                                            }
                                            miniatureArray.clear();
                                            imageByteList.clear();
                                            fdata = null;
                                            break;
                                    }
                                }

                                public void onNothingSelected(AdapterView<?> parent) {
                                    //showToast("Spinner1: unselected");
                                }
                            });
                }

                private void savePhotoToFolder(final byte[] fdata){
                        AlertDialog.Builder alert = new AlertDialog.Builder(camera.this);
                        alert.setTitle("Zapisywanie zdjęcia");
                        alert.setMessage("Czy chcesz zapisać to zdjęcie?");
                        alert.setMessage("Nazwa zdjęcia:");
                        inputCreateFolderName = new EditText(camera.this);
                        alert.setView(inputCreateFolderName);
                        alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                                File myDir = new File(pic + "/Majdan");
                                final String[] dirNames = new String[myDir.listFiles().length];
                                for (int i = 0; i < myDir.listFiles().length; i++) {
                                    dirNames[i] = myDir.listFiles()[i].getName();
                                }
                                AlertDialog.Builder alert = new AlertDialog.Builder(camera.this);
                                alert.setTitle("Wybierz Miejsce Zapisu!");
                                alert.setItems(dirNames, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                                            String d = dFormat.format(new Date());
                                            FileOutputStream fs = new FileOutputStream(pic + "/Majdan/" + dirNames[which] + "/" + inputCreateFolderName.getText().toString() + d + ".jpg");
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(fdata, 0, fdata.length);
                                            Matrix matrix = new Matrix();
                                            matrix.postRotate(90);
                                            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                                            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fs);
                                            fs.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                alert.show();
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert.show();
                }
            };
        });

        if(camParams != null){
            colorFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> getParams = camParams.getSupportedColorEffects();
                    String[] paramsList = new String[getParams.size()];
                    paramsList = getParams.toArray(paramsList);

                    AlertDialog.Builder alert = new AlertDialog.Builder(camera.this);
                    alert.setTitle("Wybierz Filtr Koloru!");
                    final String[] finalParamsList = paramsList;
                    alert.setItems(paramsList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            camParams.setColorEffect(finalParamsList[which]);
                            camera.setParameters(camParams);
                            camera.startPreview();
                        }
                    });
                    alert.show();
                }
            });

            exposure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int expourseMin = camParams.getMinExposureCompensation();
                    int expourseMax = camParams.getMaxExposureCompensation();
                    final String[] paramsList = new String[(expourseMax - expourseMin)];
                    int count = 0;
                    for(int i = expourseMin; i < expourseMax; i++){
                        paramsList[count] = Integer.toString(i);
                        count++;
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(camera.this);
                    alert.setTitle("Wybierz Ekspozycję!");
                    alert.setItems(paramsList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            camParams.setExposureCompensation(Integer.parseInt(paramsList[which]));
                            camera.setParameters(camParams);
                            camera.startPreview();
                        }
                    });
                    alert.show();
                }
            });

            bw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> getParams = camParams.getSupportedWhiteBalance();
                    String[] paramsList = new String[getParams.size()];
                    paramsList = getParams.toArray(paramsList);

                    AlertDialog.Builder alert = new AlertDialog.Builder(camera.this);
                    alert.setTitle("Wybierz Balans Bieli!");
                    final String[] finalParamsList = paramsList;
                    alert.setItems(paramsList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            camParams.setWhiteBalance(finalParamsList[which]);
                            camera.setParameters(camParams);
                            camera.startPreview();
                        }
                    });
                    alert.show();
                }
            });

            transform.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final List getParams = camParams.getSupportedPictureSizes();
                    Camera.Size result = null;
                    String[] paramsList = new String[getParams.size()];
                    for(int i = 0; i < getParams.size(); i++){
                        result = (Camera.Size)getParams.get(i);
                        paramsList[i] = result.width + " x " + result.height;
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(camera.this);
                    alert.setTitle("Wybierz Rozdzielczość Koloru!");
                    alert.setItems(paramsList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Camera.Size result = null;
                            result = (Camera.Size)getParams.get(which);
                            camParams.setPictureSize(result.width, result.height);
                            camera.setParameters(camParams);
                            camera.startPreview();
                        }
                    });
                    alert.show();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.stopPreview();
            //linijka nieudokumentowana w API, bez niej jest crash przy wznawiamiu kamery
            cameraPreview.getHolder().removeCallback(cameraPreview);
            camera.release();
            camera = null;
        }

        orientationEventListener.disable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera == null) {
            initCamera();
            initPreview();
        }
        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable();
        } else {
        }
    }
}
