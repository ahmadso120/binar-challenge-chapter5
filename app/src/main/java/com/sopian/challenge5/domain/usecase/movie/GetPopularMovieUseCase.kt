package com.sopian.challenge5.domain.usecase.movie

import androidx.lifecycle.LiveData
import com.sopian.challenge5.data.IMovieRepository
import com.sopian.challenge5.data.Result
import com.sopian.challenge5.data.source.local.entity.MovieEntity

class GetPopularMovieUseCase(
    private val movieRepository: IMovieRepository
) {
    operator fun invoke(): LiveData<Result<List<MovieEntity>>> {
        return movieRepository.getPopularMovie()
    }
}