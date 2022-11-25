package com.noveogroup.moviecatalog.data.repository

import com.noveogroup.moviecatalog.data.mapper.convert
import com.noveogroup.moviecatalog.data.network.datasource.GenreDataSource
import com.noveogroup.moviecatalog.data.network.datasource.MovieDataSource
import com.noveogroup.moviecatalog.domain.api.MoviesRepositoryInterface
import com.noveogroup.moviecatalog.domain.interactor.DataPump
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.domain.model.MovieDetails

class MoviesRepository(
    private val movieDataSource: MovieDataSource,
    private val genreDataSource: GenreDataSource
) : MoviesRepositoryInterface {

    override suspend fun loadTrendingMovies(): DataPump<Result<List<Movie>>> {
        return DataPump { page ->
            movieDataSource.loadTrendingMovies(page).map {
                it.results?.map { movieResponse ->
                    val genres = genreDataSource.getGenresByIds(
                        movieResponse.genreIds ?: emptyList()
                    )
                    movieResponse.convert(genres)
                } ?: emptyList()
            }
        }
    }

    override suspend fun loadMovieDetails(id: Long): MovieDetails {
        TODO("Not yet implemented")
    }

}
