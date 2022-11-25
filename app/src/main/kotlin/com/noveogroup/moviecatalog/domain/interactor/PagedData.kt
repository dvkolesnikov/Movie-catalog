package com.noveogroup.moviecatalog.domain.interactor

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PagedData<T>(
    private val onLoadAction: suspend (Int) -> Result<T>
) {

    private var currentPage = 1
    private var isLoading = false

    private val _dataFlow = MutableSharedFlow<T>(replay = 1)
    val dataFlow: SharedFlow<T> = _dataFlow.asSharedFlow()

    suspend fun loadNextChunk() {
        while (isLoading) {
            delay(100)
        }
        isLoading = true
        onLoadAction(currentPage).let {
            if (it.isSuccess) {
                currentPage++
                _dataFlow.emit(it.getOrThrow())
            }
        }
        isLoading = false
    }

}