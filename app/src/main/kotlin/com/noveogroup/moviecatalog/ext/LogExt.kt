package com.noveogroup.moviecatalog.ext

import android.util.Log

fun debug(
    message: String,
    tag: String = "MovieCatalog"
) {
    Log.d(tag, message)
}

fun warn(
    message: String,
    error: Throwable,
    tag: String = "MovieCatalog"
) {
    Log.w(tag, message, error)
}
