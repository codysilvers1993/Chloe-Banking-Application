package com.example.mobilebankingapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextNumberPassword2);
        loginButton = findViewById(R.id.button);
        registerButton = findViewById(R.id.button2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if the email and password match a record in the database
                boolean loginSuccessful = checkLogin(email, password);

                if (loginSuccessful) {
                    // User is authenticated, display a success message
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // Proceed to the next activity or perform other actions.
                } else {
                    // Authentication failed, display an error message
                    Toast.makeText(MainActivity.this, "Login Failed. Invalid email or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register_Activity.class);
                startActivity(intent);
            }
        });
    }

    // Method to check user creds against database
    private boolean checkLogin(String email, String password) {
        SQLiteDatabase db = openOrCreateDatabase("HostedData.db", MODE_PRIVATE, null);

        if (db == null) {
            // Handle the case where the database cannot be opened
            Toast.makeText(this, "Database Error: Cannot open database", Toast.LENGTH_SHORT).show();
            return false;
        }

        String[] projection = {"email", "password"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);

        boolean loginSuccessful = cursor.moveToFirst();

        cursor.close();
        db.close();

        if (loginSuccessful) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,Login_Activity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login Failed. Invalid email or password.", Toast.LENGTH_SHORT).show();
        }

        return loginSuccessful;
    }
}
