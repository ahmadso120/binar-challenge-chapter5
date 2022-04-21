package com.sopian.challenge5.domain.usecase.profile

import com.sopian.challenge5.data.IUserRepository
import com.sopian.challenge5.data.source.local.entity.UserEntity

class UpdateUserUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(userEntity: UserEntity) {
        return userRepository.updateUser(userEntity)
    }
}