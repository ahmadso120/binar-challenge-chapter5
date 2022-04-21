package com.sopian.challenge5.domain.usecase.profile

import androidx.lifecycle.LiveData
import com.sopian.challenge5.data.IUserRepository
import com.sopian.challenge5.data.source.local.entity.UserEntity

class GetUserUseCase(
    private val userRepository: IUserRepository
) {
    operator fun invoke(email: String): LiveData<UserEntity?> {
        return userRepository.getUser(email)
    }
}