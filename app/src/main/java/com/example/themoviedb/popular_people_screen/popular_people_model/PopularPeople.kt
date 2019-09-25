package com.example.themoviedb.popular_people_screen.popular_people_model

import java.util.ArrayList

class PopularPeople {

    var popularity: Double? = null
    lateinit var known_for_department: String
    var gender: Int = 0
    var id: Int = 0
    lateinit var profile_path: String
    var isAdult: Boolean = false
    lateinit var name: String
    lateinit var known_for: ArrayList<PeopleKnownForPojo>
}
