package com.sopian.challenge5.data.source.local

import androidx.lifecycle.LiveData
import com.sopian.challenge5.data.source.local.entity.UserEntity
import com.sopian.challenge5.data.source.local.room.UserDao

class UserLocalDataSource private constructor(private val userDao: UserDao) {
    companion object {
        @Volatile
        private var instance: UserLocalDataSource? = null

        fun getInstance(userDao: UserDao): UserLocalDataSource =
            instance ?: synchronized(this) {
                instance ?: UserLocalDataSource(userDao)
            }
    }

    fun getUser(email: String): LiveData<UserEntity?> =
        userDao.getUser(email)

    fun login(email: String, password:String): LiveData<UserEntity?> =
        userDao.login(email, password)

    suspend fun insertUser(userEntity: UserEntity) = userDao.insertUser(userEntity)

    suspend fun deleteAllUser() = userDao.deleteAll()

    suspend fun updateUser(userEntity: UserEntity) = userDao.updateUser(userEntity)
}