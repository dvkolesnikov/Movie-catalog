package com.noveogroup.moviecatalog.presentation.navigation

sealed class Screen(val route: String) {

    object MovieList : Screen(NAV_ROUTE_MOVIE_LIST)
    class MovieDetails(movieId: Long) : Screen("movie_details/$movieId")

    companion object {
        const val NAV_ROUTE_MOVIE_LIST = "movie_list"
        const val NAV_ROUTE_MOVIE_DETAILS = "movie_details/{movieId}"
    }

}
