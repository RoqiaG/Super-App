package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.text.StringEscapeUtils;

public class cameraPage extends AppCompatActivity {
    private static final String TAG = "cameraPage";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    private String currentPhotoPath;

    Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_page);
        TextureView textureView = findViewById(R.id.textureView);
        scanButton = findViewById(R.id.scanButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(cameraPage.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(cameraPage.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(cameraPage.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    dispatchTakePictureIntent();
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.myapplication.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera or storage permission denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(currentPhotoPath);
            if (imgFile.exists()) {
                new UploadTask().execute(currentPhotoPath);
            }
        }
    }

    private class UploadTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String filePath = params[0];
            String serverUrl = "https://super-app-backend.onrender.com/ocr/getOcrUrl"; // Replace with your server URL
            String link = "";
            try {
                 link = readLinkFromAPI(serverUrl,"/flutter");
            } catch (IOException e) {
                e.printStackTrace();
            }
            String boundary = "*****";
            String LINE_FEED = "\r\n";
            HttpURLConnection connection = null;
            DataOutputStream outputStream = null;
            BufferedReader reader = null;
            StringBuilder response = new StringBuilder();

            try {
                File uploadFile = new File(filePath);
                FileInputStream fileInputStream = new FileInputStream(uploadFile);

                URL url = new URL(link);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", filePath);

                outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes("--" + boundary + LINE_FEED);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + filePath + "\"" + LINE_FEED);
                outputStream.writeBytes(LINE_FEED);

                int bytesAvailable = fileInputStream.available();
                int bufferSize = Math.min(bytesAvailable, 1024 * 1024);
                byte[] buffer = new byte[bufferSize];

                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer, 0, bufferSize)) > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.writeBytes(LINE_FEED);
                outputStream.writeBytes("--" + boundary + "--" + LINE_FEED);
                outputStream.flush();
                outputStream.close();
                fileInputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                } else {
                    return new String[]{"Error: " + responseCode};
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new String[]{"Exception: " + e.getMessage()};
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return new String[]{response.toString()};
        }
        public String readLinkFromAPI(String apiUrl, String endpoint) throws IOException {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Set connection timeout and read timeout
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Connect to the API
            connection.connect();

            // Check if response code is OK
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Assuming the API response is a JSON containing the link under key "ocrUrl"
                    String jsonResponse = response.toString();
                    String link;
//                    // Parse JSON to extract the link with the key "ocrUrl"
//                    JSONObject jsonObject = new JSONObject(jsonResponse);
//                    String ocrUrl = jsonObject.getString("ocrUrl");
                    // Parse JSON to extract the link with the key "ocrUrl"
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String ocrUrl = jsonObject.getString("ocrUrl");

                        // Now you have the link, you can append "/flutter" to it
                         link = ocrUrl.trim() + endpoint; // Trim to remove any leading or trailing whitespace
                        return link;
                    } catch (JSONException e) {
                        e.printStackTrace(); // Or handle the exception as needed
                        // Return null or some default value indicating failure to parse the JSON
                        return null;
                    }


                    // Now you have the link, you can append "/flutter" to it
                     // Trim to remove any leading or trailing whitespace


                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
            else {
                // Handle error response
                // For simplicity, let's just print the response code
                System.out.println("Error response code: " + connection.getResponseCode());
                return null;
            }
        }
        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            handleApiResponse(result[0]);
            Toast.makeText(cameraPage.this, "Response received", Toast.LENGTH_SHORT).show();
//            Intent details = new Intent(cameraPage.this, View_details.class);
//            details.putExtra("serverResponse", result[0]);
//            startActivity(details);
        }

        private void handleApiResponse(String response) {
            try {
                // Ensure the response is correctly decoded as UTF-8
                String encodedResponse = new String(response.getBytes(), "UTF-8");

                // Retrieve the user_info from the Intent that started this activity
                Intent receivedIntent = getIntent();
                String user_info = receivedIntent.getStringExtra("concatenated_info");

                // Unescape Unicode sequences to get the actual Arabic characters
                String decodedResponse = StringEscapeUtils.unescapeJava(encodedResponse);
                // Store the response in an array
                // Split by multiple delimiters
                String[] responseArray = decodedResponse.split("\"[,;\\\\s]+\"");

                // Display the response array in a Toast
                StringBuilder sb = new StringBuilder();
                for (String line : responseArray) {
                    if (line.contains("status") || line.contains("orc_data")) {
                        // remove the the line from the array
                    } else {
                        sb.append(line).append("\n");
                    }
                }
                String responseString = sb.toString();
                Toast.makeText(cameraPage.this, responseString, Toast.LENGTH_LONG).show();
                // Concatenate user_info and decodedResponse
                String decodedResponse_info = null;
                if (user_info != null) {
                    decodedResponse_info = user_info + responseString;
                }

                // If needed, display the Arabic text in a Toast or any other UI component
                // Toast.makeText(cameraPage.this, decodedResponse, Toast.LENGTH_LONG).show();

                // Create a new Intent to send the data to View_details activity
                Intent details = new Intent(cameraPage.this, View_details.class);
                details.putExtra("serverResponse", responseArray);
                details.putExtra("all_info", decodedResponse_info);
                startActivity(details);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(cameraPage.this, "Error processing the response", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
