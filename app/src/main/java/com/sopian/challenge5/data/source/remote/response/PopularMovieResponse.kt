package com.sopian.challenge5.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("results") val results: List<MovieResponse> = emptyList()
)