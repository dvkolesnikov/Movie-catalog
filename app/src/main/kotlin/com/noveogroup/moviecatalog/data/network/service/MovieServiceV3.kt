package com.noveogroup.moviecatalog.data.network.service

import com.noveogroup.moviecatalog.data.network.model.GenreListResponse
import com.noveogroup.moviecatalog.data.network.model.MovieResponse
import com.noveogroup.moviecatalog.data.network.model.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServiceV3 {

    @GET("/3/discover/movie")
    suspend fun loadTrendingMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): PagedResponse<MovieResponse>

    @GET("/3/genre/movie/list")
    suspend fun loadGenres(
        @Query("language") language: String
    ): GenreListResponse
}
