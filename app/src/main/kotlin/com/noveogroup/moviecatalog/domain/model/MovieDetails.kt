package com.noveogroup.moviecatalog.domain.model

import java.time.LocalDate

data class MovieDetails(
    val id: Long,
    val posterUrl: String,
    val rating: Float,
    val voteCount: Int,
    val title: String,
    val originalTitle: String,
    val genres: List<String>,
    val overview: String,
    val tagLine: String,
    val releaseDate: LocalDate?

)
