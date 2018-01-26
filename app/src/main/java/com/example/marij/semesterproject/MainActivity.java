package com.example.marij.semesterproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    static final int Request_Image_Capture = 1;

    ImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button myButton = (Button) findViewById(R.id.myButton);
        myImageView = (ImageView) findViewById(R.id.myImageView);


        //to check if the device has camera or not for button Enabling
        //calling function
        if (!hasCamera())
            myButton.setEnabled(false);
    }

    //check for camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launching Camera
    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureDictory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String picturename = getPictureName();
        File imageFile = new File(pictureDictory, picturename);
        Uri pictureUri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);


        startActivityForResult(intent, Request_Image_Capture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Request_Image_Capture && resultCode == RESULT_OK) {
            //get photos
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            myImageView.setImageBitmap(photo);
            Toast.makeText(MainActivity.this, "Your image will be saved in Picture Directory", Toast.LENGTH_LONG).show();
        }
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "Plane place image" + timestamp + ".jpg";
    }
}
