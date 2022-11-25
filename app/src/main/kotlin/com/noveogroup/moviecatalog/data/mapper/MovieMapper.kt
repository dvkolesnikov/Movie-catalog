package com.noveogroup.moviecatalog.data.mapper

import com.noveogroup.moviecatalog.data.network.model.MovieDetailsResponse
import com.noveogroup.moviecatalog.data.network.model.MovieResponse
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.domain.model.MovieDetails

fun MovieResponse.convert(genres: List<String>): Movie {
    return Movie(
        id = id,
        rating = voteAverage ?: 0f,
        //TODO avoid img URL hardcode, load base URL from the server
        posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
        title = title ?: originalTitle ?: "",
        originalTitle = originalTitle ?: "",
        voteCount = voteCount ?: 0,
        genres = genres
    )
}

fun MovieDetailsResponse.convert(): MovieDetails {
    return MovieDetails(
        id = id,
        //TODO avoid img URL hardcode, load base URL from the server
        posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
        rating = voteAverage ?: 0f,
        voteCount = voteCount ?: 0,
        title = title ?: originalTitle ?: "",
        originalTitle = originalTitle ?: "",
        genres = genres.map { it.name },
        overview = overview ?: "",
        tagLine = tagline ?: "",
        releaseDate = releaseDate ?: ""
    )
}