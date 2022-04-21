package com.sopian.challenge5.data.source.remote

import com.sopian.challenge5.data.source.remote.network.ApiService

class MovieRemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: MovieRemoteDataSource? = null

        fun getInstance(service: ApiService): MovieRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: MovieRemoteDataSource(service)
            }
    }

    suspend fun getAllMovie() = apiService.getPopularMovie()
}