package com.kevin.imdb.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kevin.imdb.api.response.MovieResponse
import com.kevin.imdb.database.dao.MovieDao
import com.kevin.imdb.database.entities.Movie

class MovieRepository(application: Application) {

    private val movieDao: MovieDao? = Db.getInstance(application)?.movieDao()


    fun insert(movie: Movie){
        if (movie != null) InsertAsyncTask(movieDao!!).execute(movie)
    }

    fun update(movie: Movie){
        if (movie != null) UpdateAsyncTask(movieDao!!).execute(movie)
    }

    fun getMovies(): LiveData<List<Movie>> {
        return movieDao?.getMovies() ?: MutableLiveData<List<Movie>>()
    }

    fun getStoredMovies() : List<Movie>?{

        return movieDao?.getStoredMovies()
    }

    fun getStoredFavoritesMovies() : List<Movie>?{
        return movieDao?.getStoredFavoritesMovies()
    }


    fun getFavoriteMovies(): LiveData<List<Movie>> {
        return movieDao?.getFavoritesMovies() ?: MutableLiveData<List<Movie>>()
    }
    private class InsertAsyncTask(private val movieDao: MovieDao) :
        AsyncTask<Movie, Void, Void>() {
        override fun doInBackground(vararg movies: Movie?): Void? {
            for (movie in movies) {
                if (movie != null) movieDao.insertMovie(movie)
            }
            return null
        }
    }

    private class UpdateAsyncTask(private val movieDao: MovieDao) : AsyncTask<Movie, Void, Void>() {
        override fun doInBackground(vararg movie: Movie?): Void? {
            movieDao.updateMovie(movie[0]!!)
            return null
        }

    }

}