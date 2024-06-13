package com.zelianko.weatherapp

import android.app.Application
import com.zelianko.weatherapp.di.ApplicationComponent
import com.zelianko.weatherapp.di.DaggerApplicationComponent

class WeatherApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory()
            .create(this)
    }
}