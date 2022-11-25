package com.noveogroup.moviecatalog.data.network.datasource

import com.noveogroup.moviecatalog.data.network.model.MovieResponse
import com.noveogroup.moviecatalog.data.network.model.PagedResponse
import com.noveogroup.moviecatalog.data.network.service.MovieServiceV3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class MovieDataSource(private val service: MovieServiceV3) {

    suspend fun loadTrendingMovies(page: Int): Result<PagedResponse<MovieResponse>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val lang = Locale.getDefault().toLanguageTag()
                service.loadTrendingMovies(page, lang)
            }
        }
    }
}
