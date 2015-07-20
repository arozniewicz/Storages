package pl.droidsonroids.storages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (ImageView) findViewById(R.id.image_1);
        imageView2 = (ImageView) findViewById(R.id.image_2);
        imageView3 = (ImageView) findViewById(R.id.image_3);
        imageView4 = (ImageView) findViewById(R.id.image_4);

        readBitmapFromAssets();
        saveBitmapToInternalStorage();
        readBitmapFromInternalStorage();
        saveBitmapToExternalStorage();
        readBitmapFromExternalStorage();
        saveBitmapToPrivateExternalStorage();
        readBitmapFromPrivateExternalStorage();
    }

    private void readBitmapFromAssets() {
        try {
            InputStream inputStream = null;
            try {
                inputStream = getAssets().open("images/android/logo.png");
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView1.setImageBitmap(bitmap);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBitmapToInternalStorage() {
        File file = new File(getFilesDir(), "logo.png");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readBitmapFromInternalStorage() {
        File file = new File(getFilesDir(), "logo.png");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        imageView2.setImageBitmap(bitmap);
    }

    private void saveBitmapToPrivateExternalStorage() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(getExternalFilesDir(null), "logo.png");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readBitmapFromPrivateExternalStorage() {
        if (Environment.getExternalStorageState().startsWith(Environment.MEDIA_MOUNTED)) {
            File file = new File(getExternalFilesDir(null), "logo.png");
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            imageView3.setImageBitmap(bitmap);
        }
    }

    private void saveBitmapToExternalStorage() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory(), "logo.png");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readBitmapFromExternalStorage() {
        if (Environment.getExternalStorageState().startsWith(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory(), "logo.png");
            imageView4.setImageURI(Uri.fromFile(file));
        }
    }
}
