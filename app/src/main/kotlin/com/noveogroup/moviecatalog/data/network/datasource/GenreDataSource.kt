package com.noveogroup.moviecatalog.data.network.datasource

import com.noveogroup.moviecatalog.data.network.service.MovieServiceV3
import java.util.Locale

class GenreDataSource(
    private val service: MovieServiceV3
) {

    private var cachedGenres: Map<Long, String> = emptyMap()
    private var cachedGenresLanguage: String = ""

    suspend fun getGenresByIds(ids: List<Long>): List<String> {
        val currentLanguage = Locale.getDefault().toLanguageTag()
        if (cachedGenres.isEmpty() || cachedGenresLanguage != currentLanguage) {
            runCatching {
                service.loadGenres(currentLanguage)
            }.let {
                cachedGenres = it.getOrNull()?.genres?.associate { genre ->
                    genre.id to genre.name
                } ?: emptyMap()
                cachedGenresLanguage = currentLanguage
            }
        }
        return ids.mapNotNull { cachedGenres[it] }
    }
}
