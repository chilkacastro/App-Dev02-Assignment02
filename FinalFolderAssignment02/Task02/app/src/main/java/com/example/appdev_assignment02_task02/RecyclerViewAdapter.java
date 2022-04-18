package com.example.appdev_assignment02_task02;

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
import java.util.Map;

/**
 * Created by Chilka Castro on 4/10/2022.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    private List<User> usersList;
    private Context context;
    private DatabaseHelper databaseHelper;

//    private RecyclerViewClickInterface recyclerViewInterface;

    public RecyclerViewAdapter(Context context, List<User> usersList) {
        this.usersList = usersList;
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).                     // LayoutInflater to convert xml
                inflate(R.layout.child_row, parent, false);             // attach each child to the parent
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.name.setText(user.getFname() + " " + user.getLname());

        // editbutton functionality
        holder.editImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.editTextFirstName.setText(user.getFname());
                MainActivity.editTextLastName.setText(user.getLname());
                MainActivity.editPosition = user.getId();

            }
        });

        // delete button functionality
        holder.deleteImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getLayoutPosition();
                databaseHelper.deleteData(String.valueOf(user.getId()));
                usersList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void setItem(ArrayList<User> userNamesList) {
        this.usersList = userNamesList;
    }

    /**
     * ViewHolder Inner Class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        // xml views of the childrow
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
