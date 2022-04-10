package com.example.task02_usernames;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chilka Castro on 4/10/2022.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<User> usersList;
    private Context context;
    private DatabaseHelper databaseHelper;
    private static User user;

    public RecyclerViewAdapter(Context context, List<User> usersList) {
        this.usersList = usersList;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).                     // LayoutInflater to convert xml
                inflate(R.layout.child_row, parent, false);      // attach each child to the parent
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.name.setText(user.getFname() + " " + user.getLname());

        holder.editImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", user.getId());
                intent.putExtra("fname", user.getFname());
                intent.putExtra("lname", user.getLname());
                context.startActivity(intent);

            }
        });

        holder.deleteImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = user.getId() + "";
                databaseHelper.deleteData(userId);
                Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    // View Holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageButton editImgBtn, deleteImgBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView);
            editImgBtn = itemView.findViewById(R.id.editButton);
            deleteImgBtn = itemView.findViewById(R.id.deleteButton);

        }

    }
}
