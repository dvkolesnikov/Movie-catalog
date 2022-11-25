package com.noveogroup.moviecatalog.domain.di

import com.noveogroup.moviecatalog.domain.interactor.MovieInteractor
import org.koin.dsl.module

val domainModule = module {

    factory { MovieInteractor(repository = get()) }
}
