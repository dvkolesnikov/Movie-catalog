package com.noveogroup.moviecatalog.presentation

import android.app.Application
import com.noveogroup.moviecatalog.KoinInjector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInjector.inject(this)
    }
}
