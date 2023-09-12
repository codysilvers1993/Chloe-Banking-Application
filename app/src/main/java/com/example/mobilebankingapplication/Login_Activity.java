package com.example.mobilebankingapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {
    // Button functionality for account screen
    Button checkingAccountButton, savingsAccountButton, withdrawalFundsButton, depositFundsButton, signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            // finders and button sets
        checkingAccountButton = findViewById(R.id.checkingAccountBalance);
        savingsAccountButton = findViewById(R.id.savingsAccountBalance);
        withdrawalFundsButton = findViewById(R.id.withdrawButton);
        depositFundsButton = findViewById(R.id.depositButton);
        signOutButton = findViewById(R.id.signOutButton);


        // onClick functions for handling user taps for account balance/withdrawals/deposits
        checkingAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinInputDialog();
            }
        });

        savingsAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinInputDialog();
            }
        });

        withdrawalFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinInputDialog();
            }
        });

        depositFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinInputDialog();
            }
        });

        // Method to sign user out of login screen
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(Login_Activity.this, "Sign-Out Successful", Toast.LENGTH_SHORT).show();
            }
        });



    }

    // method for pin input dialog box and corresponding variables
    private void showPinInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText pinEditText = new EditText(this);
        pinEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        builder.setView(pinEditText);
        builder.setTitle("Enter Your PIN");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            //
            public void onClick(DialogInterface dialogInterface, int i) {
                String enteredPinText = pinEditText.getText().toString();
                if (!enteredPinText.isEmpty()) {
                    int enteredPin = Integer.parseInt(enteredPinText);
                    if (isValidPin(enteredPin)) {
                        Toast.makeText(Login_Activity.this, "PIN is valid. Checking balance...", Toast.LENGTH_SHORT).show();
                        // if authenticated PIN is valid display checking out balance
                        Intent intent = new Intent(Login_Activity.this, checkingAccountBalance.class);
                        startActivity(intent);
                    } else {
                        // if authenticated PIN is invalid display message
                        Toast.makeText(Login_Activity.this, "Invalid PIN", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // If no PIN display message again
                    Toast.makeText(Login_Activity.this, "Please enter a PIN", Toast.LENGTH_SHORT).show();
                }
            }
        });
            // cancel button on dialog box
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }
        // Pin database authentication
        private boolean isValidPin(int enteredPin) {
            SQLiteDatabase db = openOrCreateDatabase("YourDatabaseName.db", MODE_PRIVATE, null);

            // Replace "users" with your actual table name if it's different
            Cursor cursor = db.rawQuery("SELECT PINNumber FROM users WHERE PINNumber = ?", new String[]{String.valueOf(enteredPin)});
            boolean isValid = false;
            if (cursor != null && cursor.moveToFirst()) {
                // If a record with the entered PIN is found in the database, it's considered valid
                isValid = true;
            }
            if (cursor != null) {
                cursor.close();
            }
            db.close();

            return isValid;
        }
}
