package com.example.testapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyTask extends AsyncTask<Void, Void, Void> {
    String result;
    public List<String> imageUrls = new ArrayList<>();
    public List<Bitmap> allBitMaps = new ArrayList<>();
    @Override
    protected Void doInBackground(Void... voids) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        String result = null;
        try {
            url = new URL("https://eulerity-hackathon.appspot.com/image");
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
            //JSONArray jarr = new JSONArray(result);
            //JSONObject obj = new JSONObject(result);
            JSONArray jArray = new JSONArray(result);
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    JSONObject obj1 = jArray.getJSONObject(i);
                    imageUrls.add(obj1.getString("url"));
                }
            }
            //Toast temp = Toast.makeText(getApplicationContext(), imageUrls.get(0), Toast.LENGTH_LONG);
            //temp.show();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
            downloadImages(imageUrls);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.loadImages(allBitMaps);
    }

    private void downloadImages(List<String> imageUrls) {
        for (String s : imageUrls) {
            URL url = null;
            try {
                url = new URL(s);
                URLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                allBitMaps.add(myBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}