package com.sopian.challenge5.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopian.challenge5.Injection
import com.sopian.challenge5.domain.usecase.login.DoLoginUseCase
import com.sopian.challenge5.domain.usecase.profile.GetUserUseCase
import com.sopian.challenge5.domain.usecase.login.SetIsAuthorizedUseCase
import com.sopian.challenge5.domain.usecase.movie.GetPopularMovieUseCase
import com.sopian.challenge5.domain.usecase.profile.DeleteAllMovieUseCase
import com.sopian.challenge5.domain.usecase.profile.DeleteAllUserUseCase
import com.sopian.challenge5.domain.usecase.profile.UpdateUserUseCase
import com.sopian.challenge5.domain.usecase.register.RegisterUserUseCase
import com.sopian.challenge5.ui.home.HomeViewModel
import com.sopian.challenge5.ui.login.LoginViewModel
import com.sopian.challenge5.ui.profile.ProfileViewModel
import com.sopian.challenge5.ui.register.RegisterViewModel

class ViewModelFactory private constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val doLoginUseCase: DoLoginUseCase,
    private val setIsAuthorizedUseCase: SetIsAuthorizedUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteAllUserUseCase: DeleteAllUserUseCase,
    private val deleteAllMovieUseCase: DeleteAllMovieUseCase
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideGetPopularMovieUseCase(context),
                    Injection.provideRegisterUserUseCase(context),
                    Injection.provideDoLoginUserUseCase(context),
                    Injection.provideSetIsAuthorizedUseCase(context),
                    Injection.provideGetUserUseCase(context),
                    Injection.provideUpdateUserUseCase(context),
                    Injection.provideDeleteAllUserUseCase(context),
                    Injection.provideDeleteAllMovieUseCase(context)
                )
            }

    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(getPopularMovieUseCase) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(registerUserUseCase) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(doLoginUseCase, setIsAuthorizedUseCase) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(getUserUseCase, updateUserUseCase,
                deleteAllUserUseCase, deleteAllMovieUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}