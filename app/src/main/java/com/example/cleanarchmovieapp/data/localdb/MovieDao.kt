package com.example.cleanarchmovieapp.data.localdb

import androidx.room.*
import com.example.cleanarchmovieapp.data.MovieDbModel

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieModel: MovieDbModel)
    @Query("SELECT * FROM moviedb")
    suspend fun getPrevList(): List<MovieDbModel>
    @Query("DELETE FROM moviedb")
    suspend fun deleteAll()
}