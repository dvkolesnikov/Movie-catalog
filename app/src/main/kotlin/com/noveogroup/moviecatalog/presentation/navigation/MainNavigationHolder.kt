package com.noveogroup.moviecatalog.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.noveogroup.moviecatalog.presentation.screen.details.MovieDetailsScreen
import com.noveogroup.moviecatalog.presentation.screen.list.MovieListScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
        composable(
            route = Screen.NAV_ROUTE_MOVIE_DETAILS,
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) {
            MovieDetailsScreen(
                navController = navController,
                viewModel = koinViewModel() {
                    parametersOf(it.arguments?.getLong("movieId", -1L))
                }
            )
        }
    }
}
