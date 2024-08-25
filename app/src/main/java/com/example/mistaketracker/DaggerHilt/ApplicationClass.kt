package com.example.mistaketracker.DaggerHilt

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