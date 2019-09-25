package com.example.themoviedb.person_details_screen.person_details_view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.example.themoviedb.others.Utilities
import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModel
import com.example.themoviedb.person_details_screen.person_details_presenter.PersonDetailsPresenter
import com.example.themoviedb.person_details_screen.person_details_model.Profiles
import com.example.themoviedb.R
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople
import com.squareup.picasso.Picasso

import java.util.ArrayList

class PersonDetailsActivity : AppCompatActivity(), PersonDetailsViewInterface {

    internal lateinit var popularPeople: PopularPeople
    internal lateinit var personImage: ImageView
    internal lateinit var personName: TextView
    internal lateinit var personDep: TextView
    internal lateinit var personAdult: TextView
    private var recyclerView: RecyclerView? = null
    internal lateinit var profiles: ArrayList<Profiles>
    internal lateinit var adapter: GridAdapter
    internal lateinit var layoutManager: LinearLayoutManager
    internal lateinit var personDetailsPresenter: PersonDetailsPresenter
    internal lateinit var utilities: Utilities
    internal var name: String? = null
    internal var dep: String? = null
    internal var id = 0
    internal var adult: Boolean? = null
    internal var profile_path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        personImage = findViewById(R.id.image_id)
        personName = findViewById(R.id.nameTextViewId)
        personDep = findViewById(R.id.departmentTextViewId)
        personAdult = findViewById(R.id.adultTextViewId)
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        layoutManager = GridLayoutManager(this, 3)
        profiles = ArrayList()
        adapter = GridAdapter(profiles, this@PersonDetailsActivity)
        recyclerView!!.adapter = adapter
        personDetailsPresenter = PersonDetailsPresenter(this, PersonDetailsModel())
        utilities = Utilities()
        popularPeople = PopularPeople()

        personDetailsPresenter.getPersonDetails()

        Picasso.with(this).load(utilities.photo_first_path + popularPeople.profile_path)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(personImage)

        personName.text = name
        personDep.text = dep

        val adultStr = (adult!!).toString()
        personAdult.text = "adult : $adultStr"

        personDetailsPresenter.fetchPersonImage("https://api.themoviedb.org/3/person/$id/images?api_key=e6f20f39139b1f5a2be132cbaaa9ce43")
    }

    override fun setImage(personImage: Profiles) {
        profiles.add(personImage)
    }

    override fun setImagesInAdapter() {
        adapter.notifyDataSetChanged()
        recyclerView!!.layoutManager = layoutManager
    }

    override fun getPersonDetails() {
        val i = this.getIntent()
        val extras = i.extras

        name = extras!!.getString("person_name")
        dep = extras.getString("person_department")
        id = extras.getInt("person_id", 1)
        adult = extras.getBoolean("person_adult", true)
        profile_path = extras.getString("profile_path")

        popularPeople.name = name.toString()
        popularPeople.id = id
        popularPeople.known_for_department = dep.toString()
        popularPeople.isAdult = adult as Boolean
        popularPeople.profile_path = profile_path.toString()
    }

}