package com.noveogroup.moviecatalog.domain.model

data class Movie(
    val id: Long,
    val posterUrl: String,
    val rating: Float,
    val voteCount: Int,
    val title: String,
    val originalTitle: String,
    val genres: List<String>
)
