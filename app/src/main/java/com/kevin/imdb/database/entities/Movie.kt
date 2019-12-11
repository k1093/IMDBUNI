package com.kevin.imdb.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Movie.TABLE_NAME)
data class Movie(@PrimaryKey
                 @ColumnInfo(name = "id") var id : Int,
                 @ColumnInfo(name = "title") var title : String,
                 @ColumnInfo(name = "description") var description : String,
                 @ColumnInfo(name = "votes") var votes : Int,
                 @ColumnInfo(name = "poster") var poster : String,
                 @ColumnInfo(name = "favorite") var favorite : String,
                 @ColumnInfo(name = "see_later") var see_later : Int){

    companion object{
        const val  TABLE_NAME = "Movie"
    }


}