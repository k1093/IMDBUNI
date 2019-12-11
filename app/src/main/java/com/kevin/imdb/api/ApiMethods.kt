package com.kevin.imdb.api

import com.kevin.imdb.BuildConfig
import com.kevin.imdb.api.response.AuthenticateResponse
import com.kevin.imdb.api.response.GetMovieDetail
import com.kevin.imdb.api.response.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMethods {

    @GET("authentication/guest_session/new")
    fun getSessionId(@Query("api_key") api_key : String = BuildConfig.IMBTOKEN) : Call<AuthenticateResponse>

    //@GET("discover/movie?sort_by=popularity.desc")
    @GET("movie/now_playing")
    fun getMovies(@Query("api_key") api_key : String = BuildConfig.IMDBAPIKEY,
                  @Query("page") page : Int) : Call<GetMoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movie_id : Int,
                       @Query("api_key") api_key: String,
                       @Query("language") language : String) : Call<GetMovieDetail>
}