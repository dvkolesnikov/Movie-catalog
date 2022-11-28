package com.noveogroup.moviecatalog.data.network.datasource

import com.noveogroup.moviecatalog.data.network.model.ConfigurationResponse
import com.noveogroup.moviecatalog.data.network.service.MovieServiceV3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConfigurationDataSource(
    private val service: MovieServiceV3
) {

    private var cachedConfiguration: ConfigurationResponse? = null

    suspend fun loadPosterImageBaseUrl(): String {
        return getConfiguration()?.imagesConfig?.let {
            it.secureBaseUrl + it.posterSizes[it.posterSizes.lastIndex / 2]
        } ?: ""
    }

    suspend fun loadOriginalImageBaseUrl(): String {
        return getConfiguration()?.imagesConfig?.let {
            it.secureBaseUrl + it.posterSizes.last()
        } ?: ""
    }

    private suspend fun getConfiguration(): ConfigurationResponse? {
        if (cachedConfiguration == null) {
            withContext(Dispatchers.IO) {
                runCatching {
                    service.loadConfiguration()
                }.let {
                    if (it.isSuccess) {
                        cachedConfiguration = it.getOrNull()
                    }
                }
            }
        }
        return cachedConfiguration
    }
}
