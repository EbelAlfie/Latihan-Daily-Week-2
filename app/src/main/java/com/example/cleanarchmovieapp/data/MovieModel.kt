package com.example.cleanarchmovieapp.data

import com.example.cleanarchmovieapp.domain.MovieEntity
import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val image: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("release_date")
    val year: String,
    @SerializedName("original_title")
    val name: String,
    @SerializedName("overview")
    val desc: String,
){
    companion object {
        fun convert(it: MovieModel): MovieEntity {
            return MovieEntity(it.id, it.image, it.rating, it.year, it.name, it.desc)
        }
        fun convertList(movie: List<MovieModel>): List<MovieEntity> {
            return movie.map {
                convert(it)
            }
        }
    }
}