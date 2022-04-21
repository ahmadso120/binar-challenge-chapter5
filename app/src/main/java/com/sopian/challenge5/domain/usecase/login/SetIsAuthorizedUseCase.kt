package com.sopian.challenge5.domain.usecase.login

import com.sopian.challenge5.data.IUserRepository
import com.sopian.challenge5.data.source.local.entity.UserEntity

class SetIsAuthorizedUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(userEntity: UserEntity, isAuthorizedState: Boolean) {
        return userRepository.setIsAuthorized(userEntity, isAuthorizedState)
    }
}