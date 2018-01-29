package com.example.hong.facedetection;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class SecondActivity extends AppCompatActivity {

    private ImageView imageView;
    private final static int LOAD_IMAGE_PHONE = 0;
    private final static int LOAD_IMAGE_CARMER = 1;
    private File cameraImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageView = (ImageView) findViewById(R.id.iv_show);
    }


    public void loadFromCarmera(View view) {
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void toFaceActivity(Uri uri){
        Intent intent=new Intent(getApplicationContext(), FaceActivity.class);
        intent.putExtra("image", uri);
        startActivity(intent);
    }
}
