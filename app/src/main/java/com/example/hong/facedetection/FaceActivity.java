package com.example.hong.facedetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/*Reference: https://github.com/WuZifan*/
public class FaceActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private static final int MAXIMUM_FACE = 10;
    private static FaceDetector.Face[] faces = new Face[MAXIMUM_FACE];
    private ImageView imageView_show;
    int face_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        Intent intent = this.getIntent();
        Uri uri = (Uri) intent.getParcelableExtra("image");
        imageView_show = (ImageView) this.findViewById(R.id.iv_show);
        bitmap = ImageUtils.scaleToAndroidImage(uri, FaceActivity.this);
        imageView_show.setImageBitmap(bitmap);

        FaceDetector faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), MAXIMUM_FACE);
        Bitmap detectImage = bitmap.copy(Bitmap.Config.RGB_565, true);
        face_count = faceDetector.findFaces(detectImage, faces);
        drawCircleOnFace(face_count);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("There are "+ Integer.toString(face_count) + " People in your class today");

    }



    private void drawCircleOnFace(int face_Count) {
        Bitmap copyBitmap = ImageUtils.copyOfImage(bitmap);
        if (face_Count > 0) {
            for (int i = 0; i < face_Count; i++) {
                Face face = this.faces[i];
                PointF pointF = new PointF();
                face.getMidPoint(pointF);
                int x = (int) pointF.x;
                int y = (int) pointF.y;
                int eyeDistance = (int) face.eyesDistance();
                int radio = eyeDistance * 9 / 5;
                for (int xc = -radio; xc <= radio; xc++) {
                    for (int yc = -radio; yc <= radio; yc++) {
                        if (Math.sqrt(xc * xc + yc * yc) <= radio && Math.sqrt(xc * xc + yc * yc) >= 0.95 * radio) {
                            try {
                                copyBitmap.setPixel(x + xc, y + yc, Color.WHITE);
                            } catch (Exception exception) {
                                System.out.println("Out of bound");
                            }
                        }
                    }
                }

            }
            this.imageView_show.setImageBitmap(copyBitmap);
        }


    }


    public void retake(View view){
        Intent intent = new Intent(FaceActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
