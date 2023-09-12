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

        Intent intent = getIntent();
        if (intent != null) {
            String data = intent.getStringExtra("data");
            dataTextView.setText(data);
        }

        // Click listener for the "Clear Database Data" button
        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDatabaseData();
                dataTextView.setText("Database data has been cleared.");
            }
        });
    }

    // Method to clear database data
    private void clearDatabaseData() {
        SQLiteDatabase db = openOrCreateDatabase("HostedData.db", MODE_PRIVATE, null);

        try {
            db.execSQL("DELETE FROM users");
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
}
