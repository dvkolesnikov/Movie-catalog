package com.noveogroup.moviecatalog.domain.interactor

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.atomic.AtomicBoolean

class PagedData<T>(
    private val onLoadAction: suspend (Int) -> Result<T>
) {

    private var currentPage = 1
    private var isLoading = AtomicBoolean(false)

    private val _dataFlow = MutableSharedFlow<Result<T>>(replay = 1)
    val dataFlow: SharedFlow<Result<T>> = _dataFlow.asSharedFlow()

    suspend fun loadNextChunk() {
        while (!isLoading.compareAndSet(/* expectedValue = */ false, /* newValue = */ true)) {
            delay(100)
        }
        onLoadAction(currentPage).let {
            if (it.isSuccess) {
                currentPage++
            }
            _dataFlow.emit(it)
        }
        isLoading.set(false)
    }

}