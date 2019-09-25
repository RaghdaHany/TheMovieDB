package com.example.themoviedb.person_details_screen.person_details_view

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.example.themoviedb.person_details_screen.person_details_model.Profiles
import com.example.themoviedb.person_image_screen.person_image_view.ImageActivity
import com.example.themoviedb.R
import com.squareup.picasso.Picasso

import java.util.ArrayList


class GridAdapter(private val profiles: ArrayList<Profiles>, private val context: Context) : RecyclerView.Adapter<GridAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater

    internal var first_url_part = "https://image.tmdb.org/t/p/w500/"

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridAdapter.MyViewHolder {
        val itemView = inflater.inflate(R.layout.grid_view_item, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {

        val myHolder = holder
        holder.bind(profiles[i])
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var personImage: ImageView? = null

        init {
            personImage = itemView.findViewById<View>(R.id.gridImage) as ImageView

        }

        internal fun bind(image: Profiles) {

            Picasso.with(context).load(first_url_part + image.file_path)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(personImage)

            itemView.setOnClickListener {
                val intent = Intent(context, ImageActivity::class.java)
                intent.putExtra("picture_path", first_url_part + image.file_path)
                context.startActivity(intent)
            }
        }
    }
}