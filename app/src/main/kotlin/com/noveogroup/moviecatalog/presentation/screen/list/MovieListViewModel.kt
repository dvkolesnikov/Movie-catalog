package com.noveogroup.moviecatalog.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noveogroup.moviecatalog.domain.interactor.DataPump
import com.noveogroup.moviecatalog.domain.interactor.MovieInteractor
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.ext.debug
import com.noveogroup.moviecatalog.ext.warn
import com.noveogroup.moviecatalog.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieInteractor: MovieInteractor
) : ViewModel() {

    private var dataPump: DataPump<Result<List<Movie>>>? = null

    private val _state = MutableStateFlow(MovieListScreenState(emptyList()))
    val state = _state.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<Screen>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            movieInteractor.loadTrendingMovies().also { pump ->
                dataPump = pump
                pump.loadNextChunk()
                pump.dataFlow.collect { result ->
                    val newMovieItems = result.getOrElse { error ->
                        warn("Failed to load movie list", error)
                        emptyList()
                    }.let { newMovies ->
                        newMovies.map { MovieListItem.MovieItem(it) }
                    }
                    _state.update { screenState ->
                        screenState.copy(
                            items = screenState.items.filter { it !is MovieListItem.Loading } +
                                    newMovieItems

                        )
                    }
                }
            }
        }
    }

    fun handleListScrolledToEnd() {
        debug("Scrolled to bottom")
        viewModelScope.launch {
            _state.update { it.copy(items = it.items + listOf(MovieListItem.Loading)) }
            dataPump?.loadNextChunk()
        }
    }

    fun handleMovieClicked(movie: MovieListItem.MovieItem) {
        viewModelScope.launch {
            _navigationEvents.emit(Screen.MovieDetails(movie.movie.id))
        }
    }

}