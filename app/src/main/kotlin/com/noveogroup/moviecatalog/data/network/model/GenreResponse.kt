package com.noveogroup.moviecatalog.data.network.model

import com.squareup.moshi.Json

data class GenreResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String
)
