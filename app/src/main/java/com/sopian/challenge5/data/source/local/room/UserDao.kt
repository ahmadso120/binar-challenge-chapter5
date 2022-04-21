package com.sopian.challenge5.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sopian.challenge5.data.source.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    fun getUser(email: String) : LiveData<UserEntity?>

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun login(email: String, password: String): LiveData<UserEntity?>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}