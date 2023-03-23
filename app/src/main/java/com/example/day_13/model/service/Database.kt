package com.example.day_13.model.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.day_13.model.AlamatModel

@Database(entities = [AlamatModel::class], version = 1)
abstract class AlamatDb : RoomDatabase() {
    abstract fun alamatDao(): AlamatDao
    companion object {
        @Volatile
        private var Object: AlamatDb? = null
        @JvmStatic
        fun getDatabase(context: Context): AlamatDb {
            if (Object == null) {
                synchronized(AlamatDb::class.java) {
                    Object = Room.databaseBuilder(context.applicationContext,
                        AlamatDb::class.java, "alamat_db")
                        .build()
                }
            }
            return Object as AlamatDb
        }
    }
}