package com.example.cleanarchmovieapp.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleanarchmovieapp.data.MovieDbModel

@Database(entities = [MovieDbModel::class], version = 1)
abstract class MovieDb : RoomDatabase() {
    abstract fun MovieDao(): MovieDao
    companion object {
        @Volatile
        private var Object: MovieDb? = null
        @JvmStatic
        fun getDatabase(context: Context): MovieDb {
            if (Object == null) {
                synchronized(MovieDb::class.java) {
                    Object = Room.databaseBuilder(context.applicationContext,
                        MovieDb::class.java, "movie_db")
                        .build()
                }
            }
            return Object as MovieDb
        }
    }
}