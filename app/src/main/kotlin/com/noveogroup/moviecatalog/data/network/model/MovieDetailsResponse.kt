package com.noveogroup.moviecatalog.data.network.model

import com.squareup.moshi.Json
import java.time.LocalDate

data class MovieDetailsResponse(
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: LocalDate?,
    @Json(name = "id")
    val id: Long,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "tagline")
    val tagline: String?,
    @Json(name = "vote_count")
    val voteCount: Int?,
    @Json(name = "vote_average")
    val voteAverage: Float?,
    @Json(name = "genres")
    val genres: List<GenreResponse>
)
