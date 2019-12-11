package com.kevin.imdb.movies

import android.app.Activity
import android.content.Context
import com.kevin.imdb.BasePresenter
import com.kevin.imdb.api.OnResponseListener
import javax.inject.Inject



class MoviesPresenter /*@Inject constructor*/(activity : Activity, context : Context) : MoviesInterface, BasePresenter(context, activity) {

    private var moviesDataSource = MoviesDataSource(activity, context)
    private lateinit var onResponseListener : OnResponseListener

    override fun getMovies(page : Int) {
        moviesDataSource.getMovies(page, onResponseListener)
    }


    override fun getMovieDetail(movieId: Int) {
        moviesDataSource.getMovieDetail(movieId, onResponseListener)
    }


    override fun setOnResponseListener(onResponseListener: OnResponseListener) {
        this.onResponseListener = onResponseListener
    }



}