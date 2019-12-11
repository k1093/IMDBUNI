package com.kevin.imdb.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kevin.imdb.api.response.MovieResponse
import com.kevin.imdb.database.entities.Movie

@Dao
interface MovieDao {

    @Insert
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM ${Movie.TABLE_NAME} ORDER BY title")
    fun getMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} ORDER BY title")
    fun getStoredMovies() : List<Movie>


    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE favorite LIKE :favorite")
    fun getFavoritesMovies(favorite : String = "1") : LiveData<List<Movie>>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE favorite LIKE :favorite")
    fun getStoredFavoritesMovies(favorite : String = "1") : List<Movie>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE see_later = 1 ORDER BY title ")
    fun getSeeLaterMovies() : LiveData<List<Movie>>
}