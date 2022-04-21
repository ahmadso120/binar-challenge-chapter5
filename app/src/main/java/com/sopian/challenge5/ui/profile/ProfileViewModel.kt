package com.sopian.challenge5.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopian.challenge5.data.source.local.entity.UserEntity
import com.sopian.challenge5.domain.usecase.profile.DeleteAllMovieUseCase
import com.sopian.challenge5.domain.usecase.profile.DeleteAllUserUseCase
import com.sopian.challenge5.domain.usecase.profile.GetUserUseCase
import com.sopian.challenge5.domain.usecase.profile.UpdateUserUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    val getUserUseCase: GetUserUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val deleteAllUserUseCase: DeleteAllUserUseCase,
    val deleteAllMovieUseCase: DeleteAllMovieUseCase
) : ViewModel() {

    fun getUser(email:String) = getUserUseCase(email)

    fun updateUser(userEntity: UserEntity) {
        viewModelScope.launch {
            updateUserUseCase(userEntity)
        }
    }

    fun deleteAllMovie() {
        viewModelScope.launch {
            deleteAllMovieUseCase()
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch {
            deleteAllUserUseCase()
        }
    }
}