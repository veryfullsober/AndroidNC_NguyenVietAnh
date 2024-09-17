package com.example.baith1_bai1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ReadWebpageAsyncTask extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.TextView01);
    }
    public void readWebpage(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"http://www.mobipro.vn"});
    }
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            String line;
            for (String urlString : urls) {
                try {
                    java.net.URL url = new java.net.URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream content = connection.getInputStream();
                        BufferedReader buffer = new BufferedReader(new InputStreamReader(content));

                        while ((line = buffer.readLine()) != null) {
                            response += line;
                        }
                        buffer.close();
                    } else {
                        response = "Error: " + responseCode;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }
}
