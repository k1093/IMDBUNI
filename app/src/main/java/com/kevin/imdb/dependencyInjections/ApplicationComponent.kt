package com.kevin.imdb.dependencyInjections

import com.kevin.imdb.api.response.MovieResponse
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface ApplicationComponent {

    val movieResponse : MovieResponse
}