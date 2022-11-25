package com.noveogroup.moviecatalog.domain.api

import com.noveogroup.moviecatalog.domain.interactor.DataPump
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.domain.model.MovieDetails


interface MoviesRepositoryInterface {

    suspend fun loadTrendingMovies(): DataPump<Result<List<Movie>>>

    suspend fun loadMovieDetails(id: Long): MovieDetails
}
