package com.sopian.challenge5.data.source.remote.network

import com.sopian.challenge5.BuildConfig
import com.sopian.challenge5.data.source.remote.response.PopularMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): PopularMovieResponse
}
