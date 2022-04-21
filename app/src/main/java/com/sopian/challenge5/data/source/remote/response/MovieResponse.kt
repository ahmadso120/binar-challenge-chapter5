package com.sopian.challenge5.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: String,
)
