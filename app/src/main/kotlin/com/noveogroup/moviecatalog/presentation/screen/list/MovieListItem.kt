package com.noveogroup.moviecatalog.presentation.screen.list

import com.noveogroup.moviecatalog.domain.model.Movie

sealed class MovieListItem {

    object Loading : MovieListItem()

    data class MovieItem(
        val movie: Movie
    ) : MovieListItem()
}
