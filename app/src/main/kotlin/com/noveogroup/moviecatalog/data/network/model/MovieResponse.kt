package com.noveogroup.moviecatalog.data.network.model

import com.squareup.moshi.Json
import java.time.LocalDate

data class MovieResponse(
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "adult")
    val isAdult: Boolean?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: LocalDate?,
    @Json(name = "genre_ids")
    val genreIds: List<Long>?,
    @Json(name = "id")
    val id: Long,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "backdrop_path")
    val backDropPath: String?,
    @Json(name = "popularity")
    val popularity: Float?,
    @Json(name = "vote_count")
    val voteCount: Int?,
    @Json(name = "video")
    val isVideo: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Float?
)
