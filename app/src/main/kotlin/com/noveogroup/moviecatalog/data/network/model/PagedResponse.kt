package com.noveogroup.moviecatalog.data.network.model

import com.squareup.moshi.Json

data class PagedResponse<T>(
    @Json(name = "page")
    val page: Int?,
    @Json(name = "total_pages")
    val totalPages: Int?,
    @Json(name = "total_results")
    val totalItems: Int?,
    @Json(name = "results")
    val results: List<T>?
)