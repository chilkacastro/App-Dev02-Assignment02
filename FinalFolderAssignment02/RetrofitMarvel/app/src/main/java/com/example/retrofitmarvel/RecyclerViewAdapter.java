package com.example.retrofitmarvel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Chilka Castro on 4/9/2022.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {             // Step 1
    private Context mContext;
    private List<Hero> heroesList;
    LayoutInflater layoutInflater;

    public RecyclerViewAdapter(Context mContext, List<Hero> heroesList) {
        this.mContext = mContext;
        this.heroesList = heroesList;
    }

    //
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.child_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(heroesList.get(position).getName());
        holder.realName.setText(heroesList.get(position).getRealname());
        holder.team.setText(heroesList.get(position).getTeam());
        holder.bio.setText(heroesList.get(position).getBio());

        // Add Glide to get Image
        Glide.with(mContext)
                  .asBitmap()
                  .load(heroesList.get(position).getImageurl())  // url of the image
                  .into(holder.heroImg);  // heroImg in the Viewholder Class
    }

    @Override
    public int getItemCount() {
        return heroesList.size();
    }

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {                         // Step 2
        TextView name, realName, team, bio;
        ImageView heroImg;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView);
            realName = itemView.findViewById(R.id.realNameTextView);
            team = itemView.findViewById(R.id.teamTextView);
            bio = itemView.findViewById(R.id.bioTextView);
            heroImg = itemView.findViewById(R.id.heroImageView);
        }
    }
}
