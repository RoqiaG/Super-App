package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    EditText username,email,password;

    Button register_btn;
    TextView login;

    String Name,Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the views
        register_btn = (Button) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.logintext);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_pass);

// Set onClickListener for the register button
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from EditTexts
                String Name = username.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                // Validation checks
                if (Name.isEmpty()) {
                    username.setError("Username is required");
                    username.requestFocus();
                    return;
                }

                if (Email.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Please enter a valid email");
                    email.requestFocus();
                    return;
                }

                if (Password.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                if (Password.length() < 6) {
                    password.setError("Password must be at least 6 characters long");
                    password.requestFocus();
                    return;
                }

                // Concatenate with a proper separator, e.g., a comma
                StringBuilder concatenatedString = new StringBuilder();
                concatenatedString.append(Name).append(", ")
                        .append(Email).append(", ")
                        .append(Password);

// Convert StringBuilder to String
                String result = concatenatedString.toString();

                // Start the next activity
                Intent register_page = new Intent(Register.this, cameraPage.class);
                register_page.putExtra("concatenated_info", result);
                startActivity(register_page);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_page = new Intent(Register.this,Login.class);
                startActivity(reg_page);
            }
        });
    }
}