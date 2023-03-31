package com.example.cleanarchmovieapp.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleanarchmovieapp.data.MovieDbModel
import com.example.cleanarchmovieapp.data.localdb.MovieDao
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
@Database(entities = [MovieDbModel::class], version = 1)
abstract class LocalDbModule : RoomDatabase() {
    abstract fun MovieDao(): MovieDao
    companion object {
        @Volatile
        private var Object: LocalDbModule? = null
        @Provides
        @JvmStatic
        fun getDatabase(context: Context): LocalDbModule {
            if (Object == null) {
                synchronized(LocalDbModule::class.java) {
                    Object = Room.databaseBuilder(context.applicationContext,
                        LocalDbModule::class.java, "moviedb")
                        .build()
                }
            }
            return Object as LocalDbModule
        }
    }
}