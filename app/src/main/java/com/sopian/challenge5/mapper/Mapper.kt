package com.sopian.challenge5.mapper

import com.sopian.challenge5.data.source.local.entity.MovieEntity
import com.sopian.challenge5.data.source.remote.response.MovieResponse

object Mapper {

    fun List<MovieResponse>?.mapMovieResponseToEntity(): List<MovieEntity> =
        this?.map {
            MovieEntity(
                it.id,
                it.originalTitle,
                it.overview,
                it.posterPath,
                it.backdropPath,
                it.releaseDate,
                it.title,
                it.voteAverage
            )
        } ?: listOf()
}