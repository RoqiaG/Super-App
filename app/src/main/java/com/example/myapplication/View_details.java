package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class View_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        // Retrieve the data passed via the Intent
        String[] serverResponse = getIntent().getStringArrayExtra("serverResponse");
        String infoResponse = getIntent().getStringExtra("all_info");

        // Ensure serverResponse is not null to avoid NullPointerException
        if (serverResponse != null) {
            // Assuming the response is a comma-separated string for simplicity "[,;\\s]+"
            String[] responseData = infoResponse.split("\"[,;\\\\s]+\"");

            // Define TextView IDs in the layout
            String[] elements = {"textView1", "textView9", "textView10", "textView16", "textView17", "textView18", "textView19"};

            // Ensure there are enough elements in responseData
            int minLength = Math.min(responseData.length, elements.length);

            for (int i = 0; i < minLength; i++) {
                int resID = getResources().getIdentifier(elements[i], "id", getPackageName());
                TextView textView = findViewById(resID);
                if (textView != null) {
                    textView.setText(responseData[i]);
                }
            }
            // Store the response in an array
//            String[] responseArray = infoResponse.split(",    ");
//
//            // Display the response array in a Toast
//            StringBuilder sb = new StringBuilder();
//            for (String line : responseArray) {
//                sb.append(line).append("\n");
//            }
//            String responseString = sb.toString();
//            Toast.makeText(View_details.this, responseString, Toast.LENGTH_LONG).show();


            Button continue_btn = findViewById(R.id.button5);
            continue_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // Send serverResponse as a POST request with additional fields

//                            try {
                              //  sendPostRequest("https://super-app-backend.onrender.com/users/createNewUser", responseData);
                                new SendPostRequestTask(responseData).execute("https://super-app-backend.onrender.com/users/createNewUser\"");
//                                Intent intent = new Intent(getApplicationContext(), congratulationPage.class);
//                                startActivity(intent);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                } else {
//                    // Handle the case where serverResponse is null
//                    // For example, show an error message or default values
//                }

                }
            });
        }

//        Button continue_btn = findViewById(R.id.button5);
//        continue_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//            });
    }
    private class SendPostRequestTask extends AsyncTask<String, Void, String> {
        private String[] responseData;

        public SendPostRequestTask(String[] responseData) {
            this.responseData = responseData;
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");

                // Create JSON object with the necessary fields
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("organizationId", "663ccdd279e1d547f516ae12");
                jsonParam.put("organizationName", "Al Ahly Momken");

                int count = 0;
                for (String line : responseData) {
                    switch (count) {
                        case 2:
                            jsonParam.put("userNationalID", line);
                            break;
                        case 3:
                            jsonParam.put("username", line);
                            break;
                        case 5:
                            jsonParam.put("userPassword", line);
                            break;
                        default:
                            if (line.contains("@gmail.com")) {
                                jsonParam.put("userEmail", line);
                            }
                            break;
                    }
                    count++;
                }

                // Convert JSON object to string
                String jsonString = jsonParam.toString();
                Log.d("DEBUG", "JSON Payload: " + jsonString);

                // Write JSON string to the output stream
                try (OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = jsonString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                    Log.d("DEBUG", "OutputStream write successful");
                } catch (Exception e) {
                    Log.e("Error", "Failed to write to OutputStream: " + e.getMessage());
                    throw e;
                }

                int code = urlConnection.getResponseCode();
                Log.d("DEBUG", "Response Code: " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    // Read response from the server if needed
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        Log.d("Response", "Server response: " + response.toString());
                        return response.toString();
                    }
                } else {
                    Log.e("Error", "Failed to receive HTTP OK response from server. Response code: " + code);
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", "Exception occurred: " + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d("DEBUG", "HttpURLConnection disconnected");
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Handle the result here (e.g., update UI)
            if (result != null) {
                Log.d("DEBUG", "Post Request Successful: " + result);
            } else {
                Log.e("Error", "Post Request Failed");
            }
        }
    }
}
