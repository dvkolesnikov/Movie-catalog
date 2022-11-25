package com.noveogroup.moviecatalog.presentation.screen.details

import com.noveogroup.moviecatalog.domain.model.MovieDetails

sealed class MovieDetailsScreenState {

    object Loading : MovieDetailsScreenState()

    class Error(val message: String?) : MovieDetailsScreenState()

    class Success(val movieDetails: MovieDetails) : MovieDetailsScreenState()
}
