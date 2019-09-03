package com.example.themoviedb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.themoviedb.memory_cache.ImageLoader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopularPeopleAdapter extends RecyclerView.Adapter<PopularPeopleAdapter.MyViewHolder> {
    private Context context;
    private List<PopularPeople> popularPeopleList = Collections.emptyList();
    private LayoutInflater inflater;
    private PopularPeople currentPopularPeople;
    private int currentPos = 0 ;

    public PopularPeopleAdapter(Context context, List<PopularPeople> popularPeopleList) {
        this.context = context;
        this.popularPeopleList = popularPeopleList;
        inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PopularPeopleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row_popular_people, parent, false);
        return new PopularPeopleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        String photo_first_path = "https://image.tmdb.org/t/p/w500/";
        PopularPeople currentPopularPeople = popularPeopleList.get(position);
        myViewHolder.personName.setText(currentPopularPeople.name);
        myViewHolder.personDepartment.setText(currentPopularPeople.known_for_department);
//        myViewHolder.imageLoader.DisplayImage(photo_first_path+currentPopularPeople.profile_path, myViewHolder.personImage);
        holder.bind(popularPeopleList.get(position));
        Drawable placeholder = holder.personImage.getContext().getResources().getDrawable(R.drawable.ic_launcher_background);
        holder.personImage.setImageDrawable(placeholder);
        new LoadImage(holder.personImage).execute(photo_first_path+currentPopularPeople.profile_path);
    }

    @Override
    public int getItemCount() {
        return popularPeopleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView personImage ;
        TextView personName ;
        TextView personDepartment ;
        ImageLoader imageLoader ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            personImage = itemView.findViewById(R.id.person_image);
            personName = itemView.findViewById(R.id.person_name);
            personDepartment = itemView.findViewById(R.id.known_for_department);
            imageLoader = new ImageLoader(context);


        }
        private void bind (final PopularPeople popularPeople){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonDetailsActivity.class);
                    intent.putExtra("person_name", popularPeople.name);
                    intent.putExtra("person_department", popularPeople.known_for_department);
                    intent.putExtra("person_adult",popularPeople.adult);
                    intent.putExtra("person_popularity",popularPeople.popularity);
                    intent.putExtra("profile_path" , popularPeople.profile_path);
                    intent.putExtra("person_id" , popularPeople.id);
                    context.startActivity(intent);
                }
            });

        }



    }


}
