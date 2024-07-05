package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadTask extends AsyncTask<byte[], Void, String> {
    @Override
    protected String doInBackground(byte[]... params) {
        String serverUrl = "https://1b79-154-178-42-195.ngrok-free.app/flutter"; // Replace with your server URL
        try {
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "image/jpeg");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params[0]);
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return "Upload successful";
            } else {
                return "Error: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        Toast.makeText(cameraPage.class, result, Toast.LENGTH_SHORT).show();
//        Intent details = new Intent(cameraPage.class,View_details.class);
//        startActivity(details);
//    }
}
