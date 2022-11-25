package com.noveogroup.moviecatalog.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noveogroup.moviecatalog.presentation.screen.details.MovieDetailsScreen
import com.noveogroup.moviecatalog.presentation.screen.list.MovieListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavigationHolder() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.NAV_ROUTE_MOVIE_LIST
    ) {

        composable(route = Screen.NAV_ROUTE_MOVIE_LIST) {
            MovieListScreen(
                navController = navController,
                viewModel = koinViewModel()
            )
        }
        composable(route = Screen.NAV_ROUTE_MOVIE_DETAILS) {
            MovieDetailsScreen(
                navController = navController,
                viewModel = koinViewModel()
            )
        }
    }

}