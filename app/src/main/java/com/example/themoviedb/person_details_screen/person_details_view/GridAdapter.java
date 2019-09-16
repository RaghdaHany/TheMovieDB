package com.example.themoviedb.person_details_screen.person_details_view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.themoviedb.person_details_screen.person_details_model.Profiles;
import com.example.themoviedb.person_image_screen.person_image_view.ImageActivity;
import com.example.themoviedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder>{
    private ArrayList<Profiles> profiles;
    private Context context;
    private LayoutInflater inflater;

    String first_url_part = "https://image.tmdb.org/t/p/w500/";
    public GridAdapter(ArrayList<Profiles> profiles, Context context) {
        this.profiles = profiles;
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= inflater.inflate(R.layout.grid_view_item, viewGroup, false);
        return new GridAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        MyViewHolder myHolder= (MyViewHolder) holder;
        holder.bind(profiles.get(i));
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView personImage = null;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            personImage = (ImageView) itemView.findViewById(R.id.gridImage);

        }
        private void bind(final Profiles image){

            Picasso.with(context).load(first_url_part+ image.getFile_path())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(personImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("picture_path", first_url_part+image.getFile_path());
                    context.startActivity(intent);
                }
            });
        }
    }
}