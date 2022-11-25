package com.noveogroup.moviecatalog.data.repository

import com.noveogroup.moviecatalog.data.mapper.convert
import com.noveogroup.moviecatalog.data.network.datasource.ConfigurationDataSource
import com.noveogroup.moviecatalog.data.network.datasource.GenreDataSource
import com.noveogroup.moviecatalog.data.network.datasource.MovieDataSource
import com.noveogroup.moviecatalog.domain.api.MoviesRepositoryInterface
import com.noveogroup.moviecatalog.domain.interactor.PagedData
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.domain.model.MovieDetails

class MoviesRepository(
    private val movieDataSource: MovieDataSource,
    private val genreDataSource: GenreDataSource,
    private val configurationDataSource: ConfigurationDataSource
) : MoviesRepositoryInterface {

    override suspend fun loadTrendingMovies(): PagedData<List<Movie>> {
        return PagedData { page ->
            movieDataSource.loadTrendingMovies(page).map {
                it.results?.map { movieResponse ->
                    val genres = genreDataSource.getGenresByIds(
                        movieResponse.genreIds ?: emptyList()
                    )
                    val imageBaseUrl = configurationDataSource.loadPosterImageBaseUrl()
                    movieResponse.convert(genres, imageBaseUrl)
                } ?: emptyList()
            }
        }
    }

    override suspend fun loadMovieDetails(id: Long): Result<MovieDetails> {
        return movieDataSource.loadMovieDetails(id).map {
            it.convert(configurationDataSource.loadOriginalImageBaseUrl())
        }
    }

}
