package com.qts.gopik_money;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Utils.ImageAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    FileOutputStream fo;
    GridView gridview;
    private Dialog dialogCondition;
    ImageView CameraButton ;
    ImageView GalleryButton;
    ImageView PdfButton;
    Bitmap thumbnail;
    private int GALLERY = 1;
    Bitmap bitmap;
    private  int CAMERA = 2;
    TextView mTime;
    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         gridview = (GridView) findViewById(R.id.gridview);
        mTime = (TextView) findViewById(R.id.time);
        dialogCondition = new Dialog(MainActivity.this);
        gridview.setAdapter(new ImageAdapter(this));
        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                mTime.setText(c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
            }
            public void onFinish() {

            }
        };
        newtimer.start();

        gridview.setOnItemClickListener((parent, v, position, id) -> {
            SelectImageDailog();

});

    }
    private void SelectImageDailog() {

        dialogCondition.setContentView(R.layout.business_dailog);
        CameraButton = (ImageView) dialogCondition.findViewById(R.id.camera_button);
        GalleryButton = (ImageView) dialogCondition.findViewById(R.id.gallery_button);
        PdfButton = (ImageView) dialogCondition.findViewById(R.id.pdf_button);
        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        CameraButton.setOnClickListener(view -> {
            takePhotoFromCamera();
            dialogCondition.dismiss();
        });
        GalleryButton.setOnClickListener(view -> {
            choosePhotoFromGallary();
            dialogCondition.dismiss();
        });
        PdfButton.setOnClickListener(view -> {
            openPDFSelector();

            dialogCondition.dismiss();
        });


    }
    private void takePhotoFromCamera() {
        Intent intent = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    private void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }

    private void openPDFSelector() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 3);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent
            data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY && data != null) {

            Uri contentURI = data.getData();
            try {
                 bitmap =
                        MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                                contentURI);
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap);


                imageView.getDrawable();
                saveImage(bitmap);

            } catch (IOException e) {

                e.printStackTrace();


            }



        } else if (requestCode == CAMERA&&data!=null) {
            try{
                thumbnail = (Bitmap) data.getExtras().get("data");
                // ...

            }catch(Exception e){
                e.printStackTrace();
            }




            saveImage(thumbnail);

        } else {
            //Get Pdf File Here
        }
        super.onActivityResult(requestCode, resultCode, data);


    }
    public String saveImage(Bitmap myBitmap) {


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(

                Environment.getExternalStorageDirectory().getAbsolutePath() +
                        IMAGE_DIRECTORY);

        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdir();


        }

        try {

            File f = new
                    File(getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoneykyc1");

            f.createNewFile();

           fo = new FileOutputStream(f);


            fo.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);

            fo.close();


            SharedPref.saveStringInSharedPref(AppConstants.ML_LOAN_IMAGE, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



}