package com.example.mistaketracker.DepInjec

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application()
{
    override fun onCreate() {
        super.onCreate()
        // Initialize any global components or libraries if needed
    }
}
