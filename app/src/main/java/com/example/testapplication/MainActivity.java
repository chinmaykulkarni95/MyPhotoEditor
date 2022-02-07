package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import static android.Manifest.permission.INTERNET;

public class MainActivity extends AppCompatActivity {

    Button saveImage;
    Button addText;
    public static ImageView imageView;
    EditText editText;
    public static Bitmap bMap;
    public static int currImage = 0;
    public static List<Bitmap> bitMapList;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast a = Toast.makeText(getApplicationContext(),"Application Started", Toast.LENGTH_SHORT);
        a.show();
        ActivityCompat.requestPermissions(this, new String[]{INTERNET}, 1);

        MyTask mytask = new MyTask();
        mytask.execute();

        saveImage = (Button)findViewById(R.id.save);
        addText = (Button)findViewById(R.id.edit);
        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText)findViewById(R.id.editTextTextPersonName);

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setVisibility(View.VISIBLE);
            }
        });

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable b = (BitmapDrawable) imageView.getDrawable();
                SaveImage saveImage = new SaveImage();
                saveImage.toSave = b.getBitmap();
                saveImage.execute();
                editText.setVisibility(View.INVISIBLE);
                currImage++;
                if (currImage == bitMapList.size())
                    currImage = 0;
                imageView.setImageBitmap(bitMapList.get(currImage));
            }
        });

    }

    public static void loadImages(List<Bitmap> allBitMaps) {
        imageView.setImageBitmap(allBitMaps.get(0));
        MainActivity.bitMapList = allBitMaps;
    }
}