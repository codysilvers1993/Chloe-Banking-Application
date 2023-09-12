package com.example.mobilebankingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

public class Display_data_test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data_test);

        TextView dataTextView = findViewById(R.id.dataTextView);
        Button clearDataButton = findViewById(R.id.clearDataButton);

        // Retrieve the data passed from Register_Activity
        Intent intent = getIntent();
        if (intent != null) {
            String data = intent.getStringExtra("data");
            dataTextView.setText(data);
        }

        // Set a click listener for the "Clear Database Data" button
        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to clear database data
                clearDatabaseData();
                // Update the dataTextView to show that data has been cleared
                dataTextView.setText("Database data has been cleared.");
            }
        });
    }

    // Method to clear database data
    private void clearDatabaseData() {
        SQLiteDatabase db = openOrCreateDatabase("YourDatabaseName.db", MODE_PRIVATE, null);

        try {
            // Execute SQL command to delete all records from the "users" table
            db.execSQL("DELETE FROM users");
        } finally {
            // Close the database connection in a finally block
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
}
