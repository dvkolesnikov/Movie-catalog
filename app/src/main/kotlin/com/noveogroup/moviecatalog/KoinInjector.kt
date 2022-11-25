package com.noveogroup.moviecatalog

import com.noveogroup.moviecatalog.data.di.dataModule
import com.noveogroup.moviecatalog.domain.di.domainModule
import com.noveogroup.moviecatalog.presentation.App
import com.noveogroup.moviecatalog.presentation.di.movieDetailsModule
import com.noveogroup.moviecatalog.presentation.di.movieListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

object KoinInjector {

    fun inject(app: App) {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(app)
            modules(
                dataModule,
                domainModule,
                movieListModule,
                movieDetailsModule
            )
        }
    }
}