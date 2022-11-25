package com.noveogroup.moviecatalog.data.mapper

import com.noveogroup.moviecatalog.data.network.model.MovieDetailsResponse
import com.noveogroup.moviecatalog.data.network.model.MovieResponse
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.domain.model.MovieDetails

fun MovieResponse.convert(genres: List<String>, baseImageUrl: String): Movie {
    return Movie(
        id = id,
        rating = voteAverage ?: 0f,
        posterUrl = "$baseImageUrl$posterPath",
        title = title ?: originalTitle ?: "",
        originalTitle = originalTitle ?: "",
        voteCount = voteCount ?: 0,
        genres = genres
    )
}

fun MovieDetailsResponse.convert(baseImageUrl: String): MovieDetails {
    return MovieDetails(
        id = id,
        posterUrl = "$baseImageUrl$posterPath",
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