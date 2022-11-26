package com.noveogroup.moviecatalog.data.di

import com.noveogroup.moviecatalog.BuildConfig
import com.noveogroup.moviecatalog.data.network.datasource.ConfigurationDataSource
import com.noveogroup.moviecatalog.data.network.datasource.GenreDataSource
import com.noveogroup.moviecatalog.data.network.datasource.MovieDataSource
import com.noveogroup.moviecatalog.data.network.service.MovieServiceV3
import com.noveogroup.moviecatalog.data.repository.MoviesRepository
import com.noveogroup.moviecatalog.domain.api.MoviesRepositoryInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private val dataSourcesModule = module {

    factory { MovieDataSource(service = get()) }

    single { GenreDataSource(service = get()) }

    single { ConfigurationDataSource(service = get()) }
}

private val repositoryModule = module {

    factory<MoviesRepositoryInterface> {
        MoviesRepository(
            movieDataSource = get(),
            genreDataSource = get(),
            configurationDataSource = get()
        )
    }
}

private val restModule = module {

    factory {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().let { request ->
                    request.newBuilder().apply {
                        url(
                            request.url.newBuilder().addQueryParameter(
                                "api_key", BuildConfig.API_KEY
                            ).build()
                        )
                    }.build()
                })
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            })
            .build()
    }

    factory {
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )
    }

    factory {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(get())
            .addConverterFactory(get<MoshiConverterFactory>())
            .build()
    }

    factory { get<Retrofit>().create(MovieServiceV3::class.java) }

}

val dataModule = module {

    includes(
        restModule,
        repositoryModule,
        dataSourcesModule
    )
}
