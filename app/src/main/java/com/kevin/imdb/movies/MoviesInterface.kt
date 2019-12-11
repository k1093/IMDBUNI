package com.kevin.imdb.movies

import com.kevin.imdb.BaseInterface

interface MoviesInterface  : BaseInterface{

    fun getMovies(page : Int)

    fun getMovieDetail(movieId : Int)
}