package com.sopian.challenge5.ui.home

import androidx.lifecycle.*
import com.sopian.challenge5.data.source.local.entity.MovieEntity
import com.sopian.challenge5.domain.usecase.movie.GetPopularMovieUseCase
import com.sopian.challenge5.utils.Event

class HomeViewModel(
    getPopularMovieUseCase: GetPopularMovieUseCase
) : ViewModel() {

    private val _menuItemSelectedLiveData = MutableLiveData<Event<Int>>()
    val menuItemSelectedLiveData: LiveData<Event<Int>> = _menuItemSelectedLiveData

    private val _navigateToDetail = MutableLiveData<Event<MovieEntity>>()
    val navigateToDetail: LiveData<Event<MovieEntity>>
        get() = _navigateToDetail

    fun onMovieClicked(movie: MovieEntity) {
        _navigateToDetail.value = Event(movie)
    }

    fun onMenuItemSelected(actionId: Int) {
        _menuItemSelectedLiveData.postValue(Event(actionId))
    }

    val popularMovies =  getPopularMovieUseCase()
}