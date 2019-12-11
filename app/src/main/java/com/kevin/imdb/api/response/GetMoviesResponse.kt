package com.kevin.imdb.api.response

import com.squareup.moshi.Json
import javax.inject.Inject

class GetMoviesResponse(@Json(name = "page") var page : Int,
                        @Json(name = "total_results") var total_results : Int,
                        @Json(name = "total_pages") var total_pages : Int,
                        @Json(name = "results") var results : ArrayList<MovieResponse>)

class MovieResponse @Inject constructor(@Json(name = "popularity") var popularity : Double,
                    @Json(name = "vote_count") var vote_count : Int,
                    @Json(name = "poster_path") var poster_path : String,
                    @Json(name = "backdrop_path") var backdrop_path : String,
                    @Json(name = "id") var id : Int,
                    @Json(name = "original_title") var original_title : String,
                    @Json(name = "title") var title : String,
                    @Json(name = "overview") var overview : String,
                    @Json(name = "release_date") var release_date : String){

    var favorite = 0
    var see_later = 0
}