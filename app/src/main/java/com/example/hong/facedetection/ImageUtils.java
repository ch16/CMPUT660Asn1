package com.example.hong.facedetection;

/**
 * Created by Hong on 2018-01-24.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.view.WindowManager;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Hong on 2018-01-24.
 */

/*Reference: https://github.com/WuZifan*/


public class ImageUtils {

    public static Bitmap copyOfImage(Bitmap bitmap) {
        Bitmap copyImage;
        if (bitmap.getWidth() % 2 == 0) {
            copyImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        }else{
            copyImage = Bitmap.createBitmap(bitmap.getWidth()+1, bitmap.getHeight(), bitmap.getConfig());
        }
        Paint paint = new Paint();
        Canvas canvas = new Canvas(copyImage);

        canvas.drawBitmap(bitmap, new Matrix(), paint);
        return copyImage;
    }


    @SuppressWarnings({ "deprecation", "unused" })
    public static Bitmap scaleToAndroidImage(Uri uri, Activity activity) {
        InputStream iStream;
        try {
            iStream = activity.getContentResolver().openInputStream(uri);
            FileInputStream fileInputStream = (FileInputStream) iStream;
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);

            int image_width = opts.outWidth;
            int image_height = opts.outHeight;
            int scale = scaleCalu(activity, image_width, image_height);
            opts.inSampleSize = scale;
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);
            iStream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @SuppressWarnings({ "deprecation", "unused" })
    public static Bitmap scaleToAndroidImage(Uri uri, Activity activity, int diy_width, int diy_height) {
        InputStream iStream;
        try {
            iStream = activity.getContentResolver().openInputStream(uri);
            FileInputStream fileInputStream = (FileInputStream) iStream;
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);
            int image_width = opts.outWidth;
            int image_height = opts.outHeight;
            int scale = scale(diy_width, diy_height, image_width, image_height);
            opts.inSampleSize = scale;
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);
            iStream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @SuppressWarnings("deprecation")
    private static int scaleCalu(Activity activity, int image_width, int image_height) {
        WindowManager wManager = activity.getWindowManager();
        int phone_width = wManager.getDefaultDisplay().getWidth();
        int phone_height = wManager.getDefaultDisplay().getHeight();
        return scale(phone_width, phone_height, image_width, image_height);
    }


    private static int scale(int org_width, int org_height, int image_width, int image_height) {
        int scale = 1;
        int scale_width = image_width / org_width;
        int scale_height = image_height / org_height;
        if (scale_width >= scale_height && scale_width > 1) {
            scale = scale_width;
        } else if (scale_height > scale_width && scale_height > 1) {
            scale = scale_height;
        }
        return scale;
    }
}
