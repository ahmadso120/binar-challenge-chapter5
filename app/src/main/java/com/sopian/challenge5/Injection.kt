package com.sopian.challenge5

import android.content.Context
import com.sopian.challenge5.data.IMovieRepository
import com.sopian.challenge5.data.IUserRepository
import com.sopian.challenge5.data.MovieRepository
import com.sopian.challenge5.data.UserRepository
import com.sopian.challenge5.data.source.local.MovieLocalDataSource
import com.sopian.challenge5.data.source.local.UserLocalDataSource
import com.sopian.challenge5.data.source.local.room.AppDatabase
import com.sopian.challenge5.data.source.remote.MovieRemoteDataSource
import com.sopian.challenge5.data.source.remote.network.ApiConfig
import com.sopian.challenge5.domain.usecase.login.DoLoginUseCase
import com.sopian.challenge5.domain.usecase.profile.GetUserUseCase
import com.sopian.challenge5.domain.usecase.login.SetIsAuthorizedUseCase
import com.sopian.challenge5.domain.usecase.movie.GetPopularMovieUseCase
import com.sopian.challenge5.domain.usecase.profile.DeleteAllMovieUseCase
import com.sopian.challenge5.domain.usecase.profile.DeleteAllUserUseCase
import com.sopian.challenge5.domain.usecase.profile.UpdateUserUseCase
import com.sopian.challenge5.domain.usecase.register.RegisterUserUseCase

object Injection {

    private fun provideMovieRepository(context: Context): IMovieRepository {
        val database = AppDatabase.getInstance(context)
        val remoteDataSource = MovieRemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = MovieLocalDataSource.getInstance(database.movieDao())
        return MovieRepository.getInstance(remoteDataSource, localDataSource)
    }

    fun provideGetPopularMovieUseCase(context: Context): GetPopularMovieUseCase {
        val repository = provideMovieRepository(context)
        return GetPopularMovieUseCase(repository)
    }

    private fun provideUserRepository(context: Context) : IUserRepository {
        val database = AppDatabase.getInstance(context)
        val userLocalDataSource = UserLocalDataSource.getInstance(database.userDao())
        return UserRepository.getInstance(userLocalDataSource)
    }

    fun provideRegisterUserUseCase(context: Context) : RegisterUserUseCase {
        val repository = provideUserRepository(context)
        return RegisterUserUseCase(repository)
    }

    fun provideDoLoginUserUseCase(context: Context) : DoLoginUseCase {
        val repository = provideUserRepository(context)
        return DoLoginUseCase(repository)
    }

    fun provideSetIsAuthorizedUseCase(context: Context) : SetIsAuthorizedUseCase {
        val repository = provideUserRepository(context)
        return SetIsAuthorizedUseCase(repository)
    }

    fun provideGetUserUseCase(context: Context) : GetUserUseCase {
        val repository = provideUserRepository(context)
        return GetUserUseCase(repository)
    }

    fun provideUpdateUserUseCase(context: Context) : UpdateUserUseCase {
        val repository = provideUserRepository(context)
        return UpdateUserUseCase(repository)
    }

    fun provideDeleteAllUserUseCase(context: Context) : DeleteAllUserUseCase {
        val repository = provideUserRepository(context)
        return DeleteAllUserUseCase(repository)
    }

    fun provideDeleteAllMovieUseCase(context: Context) : DeleteAllMovieUseCase {
        val repository = provideMovieRepository(context)
        return DeleteAllMovieUseCase(repository)
    }
}