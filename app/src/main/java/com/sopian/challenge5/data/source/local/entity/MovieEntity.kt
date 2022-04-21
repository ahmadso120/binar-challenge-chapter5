package com.sopian.challenge5.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: String,
) : Parcelable {
    fun posterPathUrl() = "https://image.tmdb.org/t/p/w500/$posterPath"

    fun backdropPathUrl() = "https://image.tmdb.org/t/p/w500/$backdropPath"
}
