package com.sopian.challenge5.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.sopian.challenge5.data.source.local.MovieLocalDataSource
import com.sopian.challenge5.data.source.local.entity.MovieEntity
import com.sopian.challenge5.data.source.remote.MovieRemoteDataSource
import com.sopian.challenge5.mapper.Mapper.mapMovieResponseToEntity

interface IMovieRepository {
    fun getPopularMovie(): LiveData<Result<List<MovieEntity>>>
    suspend fun deleteAllMovie()
}
class MovieRepository private constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : IMovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            movieRemoteDataSource: MovieRemoteDataSource,
            movieLocalData: MovieLocalDataSource
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(movieRemoteDataSource, movieLocalData)
            }
    }

    override fun getPopularMovie(): LiveData<Result<List<MovieEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = movieRemoteDataSource.getAllMovie()
            val movies = response.results
            val movieList = movies.mapMovieResponseToEntity()
            movieLocalDataSource.deleteAllMovie()
            movieLocalDataSource.insertMovie(movieList)
        } catch (e: Exception) {
            Log.d("MovieRepository", "getPopularMovie: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<MovieEntity>>> = movieLocalDataSource.getAllMovie().map { Result.Success(it) }
        emitSource(localData)
    }

    override suspend fun deleteAllMovie() = movieLocalDataSource.deleteAllMovie()
}

