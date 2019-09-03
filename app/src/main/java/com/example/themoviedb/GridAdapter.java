package com.example.themoviedb;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.themoviedb.memory_cache.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ProfilesPojo> profilesPojoListList = Collections.emptyList();
    private LayoutInflater inflater;

    public GridAdapter(Context context, List<ProfilesPojo> profilesPojoListList) {
        this.context = context;
        this.profilesPojoListList = profilesPojoListList;
        inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.grid_view_item, parent, false);
        return new GridAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        ProfilesPojo currentProfilePojo = profilesPojoListList.get(position);
        myViewHolder.bind(profilesPojoListList.get(position));
        String photo_first_path = "https://image.tmdb.org/t/p/w500/";
        new LoadImage(((MyViewHolder) holder).personImages).execute(photo_first_path+currentProfilePojo.file_path);

    }

    @Override
    public int getItemCount() {
        return profilesPojoListList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView personImages ;
        ImageLoader imageLoader ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            personImages = itemView.findViewById(R.id.gridImage);
            imageLoader = new ImageLoader(context);


        }
        private void bind (final ProfilesPojo profilesPojo){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("person_image_filePath", profilesPojo.file_path);
                    context.startActivity(intent);
                }
            });

        }



    }

}