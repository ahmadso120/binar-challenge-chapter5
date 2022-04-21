package com.sopian.challenge5.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopian.challenge5.data.source.local.entity.UserEntity
import com.sopian.challenge5.domain.usecase.register.RegisterUserUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    fun registerUser(userEntity: UserEntity) {
        viewModelScope.launch {
            registerUserUseCase(userEntity)
        }
    }
}