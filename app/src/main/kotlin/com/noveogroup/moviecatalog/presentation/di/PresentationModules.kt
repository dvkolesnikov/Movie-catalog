package com.noveogroup.moviecatalog.presentation.di

import com.noveogroup.moviecatalog.presentation.screen.list.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieListModule = module {

    viewModel {
        MovieListViewModel(
            movieInteractor = get()
        )
    }
}