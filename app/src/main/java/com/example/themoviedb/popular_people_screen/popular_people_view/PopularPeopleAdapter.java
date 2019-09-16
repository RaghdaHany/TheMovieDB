package com.example.themoviedb.popular_people_screen.popular_people_view;

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

import com.example.themoviedb.person_details_screen.person_details_view.PersonDetailsActivity;
import com.example.themoviedb.R;
import com.example.themoviedb.memory_cache.ImageLoader;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class PopularPeopleAdapter extends RecyclerView.Adapter<PopularPeopleAdapter.MyViewHolder> {
    private Context context;
    private List<PopularPeople> popularPeopleList ;
    private LayoutInflater inflater;
    String photo_first_path = "https://image.tmdb.org/t/p/w500/";
    PopularPeoplePresenter popularPeoplePresenter;

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
        PopularPeople currentPopularPeople = popularPeopleList.get(position);

        holder.bind(popularPeopleList.get(position));

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
            personName.setText(popularPeople.getName());
            personDepartment.setText(popularPeople.getKnown_for_department());

            Picasso.with(context).load(photo_first_path+popularPeople.getProfile_path())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(personImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonDetailsActivity.class);
                    intent.putExtra("person_name", popularPeople.getName());
                    intent.putExtra("person_department", popularPeople.getKnown_for_department());
                    intent.putExtra("person_adult",popularPeople.isAdult());
                    intent.putExtra("person_popularity",popularPeople.getPopularity());
                    intent.putExtra("profile_path" , popularPeople.getProfile_path());
                    intent.putExtra("person_id" , popularPeople.getId());
                    context.startActivity(intent);

                }
            });

        }



    }


}
