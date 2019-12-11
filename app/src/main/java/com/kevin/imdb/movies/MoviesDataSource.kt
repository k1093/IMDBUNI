package com.kevin.imdb.movies

import android.app.Activity
import android.content.Context
import com.kevin.imdb.BuildConfig
import com.kevin.imdb.api.ApiClient
import com.kevin.imdb.api.OnResponseListener
import com.kevin.imdb.api.dataSources.DataSource
import com.kevin.imdb.api.response.GetMovieDetail
import com.kevin.imdb.api.response.GetMoviesResponse
import retrofit2.Call
import retrofit2.Response

class MoviesDataSource(activity: Activity, context: Context) : DataSource(activity, context) {


    fun getMovies(currentPage : Int, onResponseListener: OnResponseListener){
        ApiClient.create(activity, context).getMovies(BuildConfig.IMDBAPIKEY, currentPage).enqueue(object : retrofit2.Callback<GetMoviesResponse>{
            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                onResponseListener.onResponseFailure()
            }

            override fun onResponse(
                call: Call<GetMoviesResponse>,
                response: Response<GetMoviesResponse>
            ) {
                if (response.body() != null) {
                    var getMoviesResponse = response.body() as GetMoviesResponse
                    if (getMoviesResponse != null && response.code() == 200) {
                        onResponseListener.onResponseSuccess(
                            response.code(),
                            getMoviesResponse
                        )
                    } else {
                        validateErrorResponse(response, onResponseListener)
                    }
                } else {
                    validateErrorResponse(response, onResponseListener)
                }
            }

        })
    }

    fun getMovieDetail(movieId : Int, onResponseListener: OnResponseListener){

        ApiClient.create(activity, context).getMovieDetail(movieId,BuildConfig.IMDBAPIKEY, "en-US").enqueue(object : retrofit2.Callback<GetMovieDetail>{
            override fun onFailure(call: Call<GetMovieDetail>, t: Throwable) {
                onResponseListener.onResponseFailure()
            }

            override fun onResponse(
                call: Call<GetMovieDetail>,
                response: Response<GetMovieDetail>
            ) {
                if (response.body() != null) {
                    var getMovieDetail = response.body() as GetMovieDetail
                    if (getMovieDetail != null && response.code() == 200) {
                        onResponseListener.onResponseSuccess(
                            response.code(),
                            getMovieDetail
                        )
                    } else {
                        validateErrorResponse(response, onResponseListener)
                    }
                } else {
                    validateErrorResponse(response, onResponseListener)
                }
            }

        })
    }
}