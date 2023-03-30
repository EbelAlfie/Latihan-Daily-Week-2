package com.example.cleanarchmovieapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MovieDb")
data class MovieDbModel (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Int?,
    @ColumnInfo("backdrop_path")
    val image: String?,
    @ColumnInfo("vote_average")
    val rating: Float?,
    @ColumnInfo("release_date")
    val year: String?,
    @ColumnInfo("original_title")
    val name: String?,
    @ColumnInfo("overview")
    val desc: String?,
)