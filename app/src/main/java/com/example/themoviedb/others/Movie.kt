package com.example.themoviedb.others

import java.util.ArrayList

class Movie {

    internal var id: Int = 0
    internal var original_tiltle: String? = null
    internal var popularity: Double? = null
    internal var vote_count: Double? = null
    internal var adult: Boolean = false
    internal var genres_id: ArrayList<Int>? = null
    internal var original_language: String? = null
    internal var video: Boolean = false
    internal var poster_path: String? = null
    internal var backdrop_path: String? = null
    internal var title: String? = null
    internal var vote_average: Double? = null
    internal var overview: String? = null
    internal var release_date: String? = null

}
