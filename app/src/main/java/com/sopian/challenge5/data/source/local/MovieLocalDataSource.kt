package com.sopian.challenge5.data.source.local

import androidx.lifecycle.LiveData
import com.sopian.challenge5.data.source.local.entity.MovieEntity
import com.sopian.challenge5.data.source.local.room.MovieDao

class MovieLocalDataSource private constructor(private val movieDao: MovieDao) {
    companion object {
        @Volatile
        private var instance: MovieLocalDataSource? = null

        fun getInstance(movieDao: MovieDao): MovieLocalDataSource =
            instance ?: synchronized(this) {
                instance ?: MovieLocalDataSource(movieDao)
            }
    }

    fun getAllMovie(): LiveData<List<MovieEntity>> = movieDao.getAllMovie()

    suspend fun insertMovie(movieList: List<MovieEntity>) = movieDao.insertTourism(movieList)

    suspend fun deleteAllMovie() = movieDao.deleteAll()
}