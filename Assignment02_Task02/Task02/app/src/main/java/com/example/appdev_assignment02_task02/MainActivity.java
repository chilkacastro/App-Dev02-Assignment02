package com.example.appdev_assignment02_task02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper; // object of class database helper
    EditText editTextFirstName, editTextLastName;
    int userId;
    Button btnAdd, btnUpdate;
    RecyclerViewAdapter adapter;
    ArrayList<User> userNames = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create DatabaseHelper object
        databaseHelper = new DatabaseHelper(MainActivity.this);

        // Connect views from xml to java in order to get the input data which is used to put info in the Database
        editTextFirstName = findViewById(R.id.firstNameEditText);
        editTextLastName = findViewById(R.id.lastNameEditText);

        btnAdd = findViewById(R.id.addButton);
        btnUpdate = findViewById(R.id.updateButton);


        // ADD button functionality
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(editTextFirstName.getText().toString(), editTextLastName.getText().toString());
                boolean isInserted = databaseHelper.insertData(user);

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                    editTextLastName.setText("");
                    editTextFirstName.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();

                }

            }
        });

        editTextLastName.setText(getIntent().getStringExtra("lname"));
        editTextFirstName.setText(getIntent().getStringExtra("fname"));

        // UPDATE button functionality
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId = getIntent().getIntExtra("id", 0);
                User user = new User(userId, editTextFirstName.getText().toString(),
                        editTextLastName.getText().toString());
                boolean isUpdated = databaseHelper.update(user);
                if (isUpdated) {
                    Toast.makeText(MainActivity.this, "Data updated successfully",
                            Toast.LENGTH_SHORT).show();
                    editTextLastName.setText("");
                    editTextFirstName.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Data not updated",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        // RecyclerView
        recyclerView = findViewById(R.id.usersRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // fetch all data from database
        userNames = databaseHelper.readAllData();
        if(userNames.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new RecyclerViewAdapter(this, userNames); // send data to recyclerviewadapter
            recyclerView.setAdapter(adapter);


        }else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no user in the database. Start adding now",
                    Toast.LENGTH_LONG).show();
        }

    }
}