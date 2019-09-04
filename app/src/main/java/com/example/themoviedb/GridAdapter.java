package com.example.themoviedb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.themoviedb.memory_cache.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        Profiles profile_picture = profiles.get(i);

        Drawable placeholder = holder.personImage.getContext().getResources().getDrawable(R.drawable.ic_launcher_background);
        holder.personImage.setImageDrawable(placeholder);
        new LoadImage(holder.personImage).execute(first_url_part+ profile_picture.getFile_path());

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
        private void bind(final Profiles prof){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("picture_path", first_url_part+prof.getFile_path());
                    context.startActivity(intent);
                }
            });
        }
    }
}