package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CameraLoginPage extends AppCompatActivity {

    private static final String TAG = "cameraPage";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    private String currentPhotoPath;



    Button scanButton;

    String url;
    String national_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_login_page);

        TextureView textureView = findViewById(R.id.textureView);
        scanButton = findViewById(R.id.scanButton);

        ApiService apiService = ApiClient2.getClient().create(ApiService.class);
        Call<ResponseModel> call = apiService.getYourData();

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    url = data.getOcrUrl();
                    Log.d(TAG, "onResponse:"+url);
                    // Handle the response
                    Log.d(TAG, "Data: " + data.toString());
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e(TAG, "Network error: " + t.getMessage());
            }
        });


        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(CameraLoginPage.this, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(CameraLoginPage.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CameraLoginPage.this,
                            new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                link = readLinkFromAPI(url,"/login_id");
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

                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String ocrUrl = jsonObject.getString("ocrUrl");

                        // Now you have the link, you can append "/flutter" to it
                        link = ocrUrl.trim() + endpoint; // Trim to remove any leading or trailing whitespace
                        return link;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }

                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
            else {

                System.out.println("Error response code: " + connection.getResponseCode());
                return null;
            }
        }
        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            handleApiResponse(result[0]);
            Toast.makeText(CameraLoginPage.this, "Response received", Toast.LENGTH_SHORT).show();
        }

        private void handleApiResponse(String response) {
//            national_id = "01234567891011";
            national_id = response;

            // Display the response in a Toast for debugging purposes
            Toast.makeText(CameraLoginPage.this, "Server Response: " + national_id, Toast.LENGTH_LONG).show();

            checkNI();

        }


    }

    public void checkNI() {
        checkNILoginRequest niRequest = new checkNILoginRequest();
        niRequest.setNational_id(national_id);

        Call<checkNILoginResponse> NIresponse = ApiClient.getUserService().NIcheck(niRequest);

        NIresponse.enqueue(new Callback<checkNILoginResponse>() {
            @Override
            public void onResponse(Call<checkNILoginResponse> call, Response<checkNILoginResponse> response) {

                if (response.isSuccessful()){


                    checkNILoginResponse checkNi = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (checkNi.isExists() == true) {
                                Toast.makeText(CameraLoginPage.this,"login Successfully" , Toast.LENGTH_LONG).show();
                                Intent home = new Intent(CameraLoginPage.this,Login.class);
                                startActivity(home);
                            }
                            else {
                                Toast.makeText(CameraLoginPage.this,"Failed",Toast.LENGTH_LONG).show();
                            }

                        }
                    },100);

                } else {
                    Toast.makeText(CameraLoginPage.this,"login Failed" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<checkNILoginResponse> call, Throwable t) {
                Toast.makeText(CameraLoginPage.this,"Throwable" + t.getLocalizedMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

}