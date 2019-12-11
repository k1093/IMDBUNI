package com.kevin.imdb.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kevin.imdb.database.entities.Movie

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository = MovieRepository(application)

    val movies = movieRepository.getMovies()

    var favoriteMovies = movieRepository.getFavoriteMovies()

    fun getStoredMovies() : List<Movie>?{
        return movieRepository.getStoredMovies()
    }

    fun getStoredFavoritesMovies(): List<Movie>?{
        return movieRepository.getStoredFavoritesMovies()
    }

    fun saveMovie(movie: Movie){
        movieRepository.insert(movie)
    }

    fun updateMovie(movie: Movie){
        movieRepository.update(movie)
    }

    fun saveIfNotExists(movieId : Int) : Boolean{

        if(movies.value != null) {
            movies.value!!.forEach {
                    movie ->
                        if(movie.id == movieId){
                            return true
                        }

            }
        }

        return false
    }
}