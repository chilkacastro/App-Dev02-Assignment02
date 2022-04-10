package com.example.assignment02_task02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper; // object of class database helper
    EditText editTextFirstName, editTextLastName;
    Button btnAdd, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create DatabaseHelper object
        databaseHelper = new DatabaseHelper(this);

        // Connect views from xml to java in order to get the input data which is used to put info in the Database
        editTextFirstName = findViewById(R.id.firstNameEditText);
        editTextLastName = findViewById(R.id.lastNameEditText);

        btnAdd = findViewById(R.id.addButton);
        btnUpdate = findViewById(R.id.updateButton);

        // ADD button functionality
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = databaseHelper.insertData(editTextFirstName.getText().toString(),
                        editTextLastName.getText().toString());

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();

                }
            }
        });

        // UPDATE button functionality
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the insertMethod of the DatabaseHelper returns a boolean so you need a boolean variable
                boolean isUpdated = databaseHelper.update(editTextFirstName.getText().toString(), editTextLastName.getText().toString());
                if (isUpdated) {
                    Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();

                }
            }

        });
    }
}