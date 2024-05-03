package com.zelianko.weatherapp.domain.repository

import com.zelianko.weatherapp.domain.entity.City
import com.zelianko.weatherapp.domain.entity.Forecast
import com.zelianko.weatherapp.domain.entity.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(cityId: Int): Weather
    suspend fun getForecast(cityId: Int): Forecast
}