package com.noveogroup.moviecatalog.domain.interactor

import com.noveogroup.moviecatalog.domain.api.MoviesRepositoryInterface
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.domain.model.MovieDetails

class MovieInteractor(
    private val repository: MoviesRepositoryInterface
) {

    suspend fun loadTrendingMovies(): PagedData<List<Movie>> {
        return repository.loadTrendingMovies()
    }

    suspend fun loadMovieDetails(id: Long): Result<MovieDetails> {
        return repository.loadMovieDetails(id)
    }
}
