package com.kevin.imdb.api.response

import com.squareup.moshi.Json

class GetMovieDetail(@Json(name = "adult") var adult : Boolean,
                     @Json(name = "backdrop_path") var backdrop_path : String,
                     @Json(name = "belongs_to_collection") var belongs_to_collection : BelongToCollection,
                     @Json(name = "genres") var genres : ArrayList<Genre>,
                     @Json(name = "homepage") var homepage : String,
                     @Json(name = "id") var id : Int,
                     @Json(name = "poster_path") var poster_path : String,
                     @Json(name = "original_title") var original_title : String,
                     @Json(name = "overview") var overview : String,
                     @Json(name = "popularity") var popularity : Double,
                     @Json(name = "vote_average") var vote_average : Double,
                     @Json(name = "vote_count") var vote_count : Int)

class BelongToCollection(@Json(name = "id") var id : Int,
                         @Json(name = "name") var name : String,
                         @Json(name = "poster_path") var poster_path : String,
                         @Json(name = "backdrop_path") var backdrop_path : String)

class Genre(@Json(name = "id") var id : Int,
            @Json(name = "name") var name : String)