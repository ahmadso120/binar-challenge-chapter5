package com.sopian.challenge5.domain.usecase.register

import com.sopian.challenge5.data.IUserRepository
import com.sopian.challenge5.data.source.local.entity.UserEntity

class RegisterUserUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(userEntity: UserEntity) {
        return userRepository.insertUser(userEntity)
    }
}