package com.example.mobilebankingapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Register_Activity extends AppCompatActivity {
// Registration functionality for user to input info and database will store data in database
    EditText emailEditText, passwordEditText, phoneEditText, dobEditText, PINEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        phoneEditText = findViewById(R.id.editTextPhone);
        dobEditText = findViewById(R.id.editTextDateOfBirth);
        PINEditText = findViewById(R.id.PIN);

        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String dob = dobEditText.getText().toString();
                String PINNumber = PINEditText.getText().toString();

                insertData(email, password, phone, dob, PINNumber);
            }
        });
    }

    private void insertData(String email, String password, String phone, String dob, String PINNumber) {
        SQLiteDatabase db = openOrCreateDatabase("HostedData.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, phone TEXT, dob TEXT, PINNumber INTEGER)");

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        values.put("phone", phone);
        values.put("dob", dob);

        try {
            // Parse PINNumber to an integer before inserting it
            int pinNumber = Integer.parseInt(PINNumber);
            values.put("PINNumber", pinNumber);
        } catch (NumberFormatException e) {
            Toast.makeText(Register_Activity.this, "Invalid PIN Number", Toast.LENGTH_SHORT).show();
            db.close();
            return;
        }

        long newRowId = db.insert("users", null, values);

        if (newRowId != -1) {
            Toast.makeText(Register_Activity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Register_Activity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
        }

        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        StringBuilder dataBuilder = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String userData = "Email: " + cursor.getString(cursor.getColumnIndex("email")) + "\n" +
                        "Password: " + cursor.getString(cursor.getColumnIndex("password")) + "\n" +
                        "Phone: " + cursor.getString(cursor.getColumnIndex("phone")) + "\n" +
                        "Date of Birth: " + cursor.getString(cursor.getColumnIndex("dob")) + "\n\n"+
                        "PIN: " + cursor.getInt(cursor.getColumnIndex("PINNumber")) + "\n\n"; // Retrieve as an integer

                dataBuilder.append(userData);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();

        Intent displayIntent = new Intent(Register_Activity.this, Display_data_test.class);
        displayIntent.putExtra("data", dataBuilder.toString());
        startActivity(displayIntent);
    }
}
