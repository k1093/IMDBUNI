package com.kevin.imdb.dependencyInjections.interfaces

import com.kevin.imdb.dependencyInjections.ApplicationComponent

interface ComponentProvider {

    val applicationComponent : ApplicationComponent
}