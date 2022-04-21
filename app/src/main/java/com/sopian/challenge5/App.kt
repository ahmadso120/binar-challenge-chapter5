package com.sopian.challenge5

import android.app.Application
import com.google.android.material.color.DynamicColors

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        GlideApp.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this).trimMemory(level)
    }
}