package com.example.cleanarchmovieapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviedb")
data class MovieDbModel (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "backdrop path")
    val image: String?,
    @ColumnInfo(name = "vote average")
    val rating: Float?,
    @ColumnInfo(name = "release date")
    val year: String?,
    @ColumnInfo(name = "original title")
    val name: String?,
    @ColumnInfo(name = "overview")
    val desc: String?,
)