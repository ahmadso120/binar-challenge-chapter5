package com.sopian.challenge5.domain.usecase.profile

import com.sopian.challenge5.data.IMovieRepository

class DeleteAllMovieUseCase(
    private val movieRepository: IMovieRepository
) {
    suspend operator fun invoke() {
        return movieRepository.deleteAllMovie()
    }
}