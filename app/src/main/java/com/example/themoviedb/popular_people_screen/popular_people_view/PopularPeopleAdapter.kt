package com.example.themoviedb.popular_people_screen.popular_people_view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.themoviedb.person_details_screen.person_details_view.PersonDetailsActivity
import com.example.themoviedb.R
import com.example.themoviedb.memory_cache.ImageLoader
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter
import com.squareup.picasso.Picasso

import java.util.Collections

class PopularPeopleAdapter(private val context: Context, private val popularPeopleList: List<PopularPeople>) : RecyclerView.Adapter<PopularPeopleAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    internal var photo_first_path = "https://image.tmdb.org/t/p/w500/"
    internal var popularPeoplePresenter: PopularPeoplePresenter? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPeopleAdapter.MyViewHolder {
        val itemView = inflater.inflate(R.layout.row_popular_people, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val myViewHolder = holder
        val currentPopularPeople = popularPeopleList[position]

        holder.bind(popularPeopleList[position])

    }

    override fun getItemCount(): Int {
        return popularPeopleList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var personImage: ImageView
        internal var personName: TextView
        internal var personDepartment: TextView
        internal var imageLoader: ImageLoader

        init {

            personImage = itemView.findViewById(R.id.person_image)
            personName = itemView.findViewById(R.id.person_name)
            personDepartment = itemView.findViewById(R.id.known_for_department)
            imageLoader = ImageLoader(context)


        }

        internal fun bind(popularPeople: PopularPeople) {
            personName.text = popularPeople.name
            personDepartment.text = popularPeople.known_for_department

            Picasso.with(context).load(photo_first_path + popularPeople.profile_path)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(personImage)

            itemView.setOnClickListener {
                val intent = Intent(context, PersonDetailsActivity::class.java)
                intent.putExtra("person_name", popularPeople.name)
                intent.putExtra("person_department", popularPeople.known_for_department)
                intent.putExtra("person_adult", popularPeople.isAdult)
                intent.putExtra("person_popularity", popularPeople.popularity)
                intent.putExtra("profile_path", popularPeople.profile_path)
                intent.putExtra("person_id", popularPeople.id)
                context.startActivity(intent)
            }

        }


    }
}