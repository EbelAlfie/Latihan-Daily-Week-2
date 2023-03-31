package com.example.cleanarchmovieapp.data.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanarchmovieapp.data.MovieDbModel
import com.example.cleanarchmovieapp.data.MovieModel
import com.example.cleanarchmovieapp.domain.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movieModel: MovieDbModel)
    @Query("SELECT * FROM moviedb")
    fun getPrevList(): List<MovieDbModel>
}