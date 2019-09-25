package com.example.themoviedb.popular_people_screen.popular_people_model

import java.util.ArrayList

class PeopleKnownForPojo {

    internal var id: Int = 0
    internal var original_tiltle: String? = null
    internal var popularity: Double? = null
    internal var vote_count: Double? = null
    internal var adult: Boolean = false
    internal var genre_ids: ArrayList<Int>? = null
    internal var original_language: String? = null
    internal var video: Boolean = false
    internal var poster_path: String? = null
    internal var backdrop_path: String? = null
    internal var title: String? = null
    internal var vote_average: Double? = null
    internal var overview: String? = null
    internal var release_date: String? = null
    internal var media_type: String? = null
    internal var first_air_date: String? = null
    internal var origin_country: ArrayList<String>? = null
}
