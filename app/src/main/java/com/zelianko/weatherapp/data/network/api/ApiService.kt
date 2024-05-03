package com.zelianko.weatherapp.data.network.api

import com.zelianko.weatherapp.data.network.dto.CityDto
import com.zelianko.weatherapp.data.network.dto.WeatherCurrentDto
import com.zelianko.weatherapp.data.network.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json?key=1b5fde6abc9d403aac7123011240305")
    suspend fun loadCurrentWeather(
        @Query("q") query: String
    ): WeatherCurrentDto

    @GET("forecast.json?key=1b5fde6abc9d403aac7123011240305")
    suspend fun loadForecast(
        @Query("q") query: String,
        @Query("days") daysCount: Int = 4
    ): WeatherForecastDto

    @GET("search.json?key=1b5fde6abc9d403aac7123011240305")
    suspend fun searchCity(
        @Query("q") query: String
    ): List<CityDto>
}