package com.noveogroup.moviecatalog.data.network.model

import com.squareup.moshi.Json

data class GenreListResponse(
    @Json(name = "genres")
    val genres: List<GenreResponse>
)
