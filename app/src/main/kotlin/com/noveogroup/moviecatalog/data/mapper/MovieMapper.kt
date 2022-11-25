package com.noveogroup.moviecatalog.data.mapper

import com.noveogroup.moviecatalog.data.network.model.MovieResponse
import com.noveogroup.moviecatalog.domain.model.Movie

fun MovieResponse.convert(genres: List<String>): Movie {
    return Movie(
        id = id,
        rating = voteAverage ?: 0f,
        posterUrl = "https://image.tmdb.org/t/p/w500$posterPath", //TODO
        title = title ?: originalTitle ?: "",
        originalTitle = originalTitle ?: "",
        voteCount = voteCount ?: 0,
        genres = genres
    )
}
