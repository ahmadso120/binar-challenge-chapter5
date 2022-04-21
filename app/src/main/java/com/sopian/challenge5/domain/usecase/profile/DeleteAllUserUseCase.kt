package com.sopian.challenge5.domain.usecase.profile

import com.sopian.challenge5.data.IUserRepository

class DeleteAllUserUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke() {
        return userRepository.deleteAllUser()
    }
}