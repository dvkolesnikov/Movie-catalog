package com.noveogroup.moviecatalog

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInjector.inject(this)
    }
}
