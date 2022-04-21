package com.sopian.challenge5.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sopian.challenge5.data.source.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllMovie(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTourism(tourism: List<MovieEntity>)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()
}