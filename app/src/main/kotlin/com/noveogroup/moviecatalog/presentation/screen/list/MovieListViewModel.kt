package com.noveogroup.moviecatalog.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noveogroup.moviecatalog.domain.interactor.MovieInteractor
import com.noveogroup.moviecatalog.domain.interactor.PagedData
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.ext.debug
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

    private var dataPump: PagedData<List<Movie>>? = null

    private val _state = MutableStateFlow(MovieListScreenState(emptyList()))
    val state = _state.asStateFlow()

    private val _errorEvents = MutableSharedFlow<String?>()
    val errorEvents = _errorEvents.asSharedFlow()

    private val _navigationEvents = MutableSharedFlow<Screen>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            movieInteractor.loadTrendingMovies().also { pump ->
                dataPump = pump
                pump.loadNextChunk()
                pump.dataFlow.collect { newMoviesResult ->
                    val newMovies = newMoviesResult.getOrNull() ?: emptyList()
                    _state.update { screenState ->
                        screenState.copy(
                            items = screenState.items.filter { it !is MovieListItem.Loading } +
                                newMovies.map { MovieListItem.MovieItem(it) }
                        )
                    }
                    newMoviesResult.exceptionOrNull()?.localizedMessage?.let { error ->
                        _errorEvents.emit(error)
                    }
                }
            }
        }
    }

    fun handleRefreshClicked() {
        viewModelScope.launch {
            _errorEvents.emit(null)
        }
        loadNextChunkOfData()
    }

    fun handleListScrolledToEnd() {
        debug("Scrolled to bottom")
        loadNextChunkOfData()
    }

    private fun loadNextChunkOfData() {
        viewModelScope.launch {
            _state.update { previousState ->
                previousState.copy(
                    items = previousState.items.filter { it !is MovieListItem.Loading } +
                        listOf(MovieListItem.Loading)
                )
            }
            dataPump?.loadNextChunk()
        }
    }

    fun handleMovieClicked(movie: MovieListItem.MovieItem) {
        viewModelScope.launch {
            _navigationEvents.emit(Screen.MovieDetails(movie.movie.id))
        }
    }
}
