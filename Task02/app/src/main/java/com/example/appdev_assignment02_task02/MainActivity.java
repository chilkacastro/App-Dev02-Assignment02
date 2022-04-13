package com.example.appdev_assignment02_task02;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper; // object of class database helper
    static EditText editTextFirstName, editTextLastName;
    int userId;
    Button btnAdd, btnUpdate;
    RecyclerViewAdapter adapter;
    ArrayList<User> userNamesList = new ArrayList<User>();
    static int editPosition;
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

        //Set up the RecyclerView to be used
        recyclerView = findViewById(R.id.usersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // fetch all data from database
        userNamesList = databaseHelper.readAllData();
        // send data to recyclerviewadapter
        adapter = new RecyclerViewAdapter(this, userNamesList);
        // set adapter object to be used by the recylerview
        recyclerView.setAdapter(adapter);

        // show a message if there is no user in the database
        if (userNamesList.size() == 0) {
            Toast.makeText(this, "There is no user in the database.",
                    Toast.LENGTH_LONG).show();
        }

        // ADD button functionality
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(editTextFirstName.getText().toString(),
                        editTextLastName.getText().toString());
                boolean isInserted = databaseHelper.insertData(user);

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Data inserted successfully",
                            Toast.LENGTH_SHORT).show();
                    editTextLastName.setText("");
                    editTextFirstName.setText("");
                    userNamesList.add(user);
                    adapter.notifyItemInserted(userNamesList.size() - 1);

                } else {
                    Toast.makeText(MainActivity.this, "Data not inserted",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    // UPDATE button functionality
    btnUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            User user = new User(editPosition, editTextFirstName.getText().toString(),
                    editTextLastName.getText().toString());
            boolean isUpdated = databaseHelper.update(user);
            if (isUpdated) {
                Toast.makeText(MainActivity.this, "Data updated successfully",
                        Toast.LENGTH_SHORT).show();
                editTextLastName.setText("");
                editTextFirstName.setText("");
                adapter.setItem(databaseHelper.readAllData());
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(MainActivity.this, "Data not updated",
                        Toast.LENGTH_SHORT).show();
            }

        }
    });
    }
}
