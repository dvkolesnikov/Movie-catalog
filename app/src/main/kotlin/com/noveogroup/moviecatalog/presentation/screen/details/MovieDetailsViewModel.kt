package com.noveogroup.moviecatalog.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noveogroup.moviecatalog.domain.interactor.MovieInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    movieId: Long,
    movieInteractor: MovieInteractor
) : ViewModel() {

    private val _state: MutableStateFlow<MovieDetailsScreenState> =
        MutableStateFlow(MovieDetailsScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            movieInteractor.loadMovieDetails(movieId).let { result ->
                if (result.isSuccess) {
                    _state.update { MovieDetailsScreenState.Success(result.getOrThrow()) }
                } else {
                    _state.update {
                        MovieDetailsScreenState.Error(
                            result.exceptionOrNull()?.localizedMessage
                        )
                    }
                }
            }
        }
    }
}
