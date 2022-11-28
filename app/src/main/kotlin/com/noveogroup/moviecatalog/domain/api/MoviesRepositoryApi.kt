package com.noveogroup.moviecatalog.domain.api

import com.noveogroup.moviecatalog.domain.interactor.PagedData
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.domain.model.MovieDetails

interface MoviesRepositoryApi {

    suspend fun loadTrendingMovies(): PagedData<List<Movie>>

    suspend fun loadMovieDetails(id: Long): Result<MovieDetails>
}
