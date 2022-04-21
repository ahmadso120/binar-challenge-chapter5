package com.sopian.challenge5.domain.usecase.login

import androidx.lifecycle.LiveData
import com.sopian.challenge5.data.IUserRepository
import com.sopian.challenge5.data.source.local.entity.UserEntity

class DoLoginUseCase(
    private val userRepository: IUserRepository
) {
    operator fun invoke(email: String, password: String): LiveData<UserEntity?> {
        return userRepository.login(email, password)
    }
}