package com.kevin.imdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kevin.imdb.api.response.MovieResponse
import com.kevin.imdb.database.dao.MovieDao
import com.kevin.imdb.database.entities.Movie


@Database(entities = [Movie::class], version = 1)
abstract class Db : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private const val DATABASE_NAME = "imdb_database"
        @Volatile
        private var INSTANCE: Db? = null

        fun getInstance(context: Context): Db? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    Db::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }
    }

}