package com.example.testapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class SaveImage extends AsyncTask<Void, Void, Void> {
    public Bitmap toSave = null;
    @Override
    protected Void doInBackground(Void... voids) {
        //https://eulerity-hackathon.appspot.com/upload
        URL url = null;
        try {
            url = new URL("https://eulerity-hackathon.appspot.com/upload");
            URLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONObject obj = new JSONObject(result);
            String saveUrl = obj.getString("url");
            URL url1 = new URL(saveUrl);
            HttpURLConnection urlConnection1 = (HttpURLConnection) url1.openConnection();
            urlConnection1.setRequestMethod("POST");
            OutputStream out = new BufferedOutputStream(urlConnection1.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(toSave.toString());
            writer.flush();
            writer.close();
            out.close();
            urlConnection1.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
