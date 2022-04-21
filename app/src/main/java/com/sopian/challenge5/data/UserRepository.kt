package com.sopian.challenge5.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sopian.challenge5.data.source.local.UserLocalDataSource
import com.sopian.challenge5.data.source.local.entity.UserEntity

interface IUserRepository {
    fun getUser(email: String): LiveData<UserEntity?>
    fun login(email: String, password: String): LiveData<UserEntity?>
    suspend fun insertUser(userEntity: UserEntity)
    suspend fun updateUser(userEntity: UserEntity)
    suspend fun deleteAllUser()
    suspend fun setIsAuthorized(userEntity: UserEntity, isAuthorizedState: Boolean)
}

class UserRepository private constructor(
    private val userLocalDataSource: UserLocalDataSource
) : IUserRepository {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userLocalDataSource: UserLocalDataSource
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userLocalDataSource)
            }
    }

    override fun getUser(email: String): LiveData<UserEntity?> =
        userLocalDataSource.getUser(email)

    override fun login(email: String, password: String): LiveData<UserEntity?> =
        userLocalDataSource.login(email, password)

    override suspend fun insertUser(userEntity: UserEntity) =
        userLocalDataSource.insertUser(userEntity)

    override suspend fun updateUser(userEntity: UserEntity) =
        userLocalDataSource.updateUser(userEntity)

    override suspend fun deleteAllUser() =
        userLocalDataSource.deleteAllUser()

    override suspend fun setIsAuthorized(userEntity: UserEntity, isAuthorizedState: Boolean) {
        userEntity.isAuthorized = isAuthorizedState
        userLocalDataSource.updateUser(userEntity)
    }
}