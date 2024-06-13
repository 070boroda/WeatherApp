package com.zelianko.weatherapp.data.mapper

import com.zelianko.weatherapp.data.network.dto.WeatherCurrentDto
import com.zelianko.weatherapp.data.network.dto.WeatherDto
import com.zelianko.weatherapp.data.network.dto.WeatherForecastDto
import com.zelianko.weatherapp.domain.entity.Forecast
import com.zelianko.weatherapp.domain.entity.Weather
import java.util.Calendar
import java.util.Date

fun WeatherCurrentDto.toEntity(): Weather = current.toEntity()
fun WeatherDto.toEntity(): Weather = Weather(
    tempC = tempC,
    conditionText = conditionDto.text,
    conditionUrl = conditionDto.iconUrl.correctImageUrl(),
    date = date.toCalendar()
)

fun WeatherForecastDto.toEntity() = Forecast(
    currentWeather = current.toEntity(),
    upcoming = forecastDto.forecastDay.drop(1).map { dayDto ->
        val dayWeatherDto = dayDto.dayWeatherDto
        Weather(
            tempC = dayWeatherDto.tempC,
            conditionText = dayWeatherDto.conditionDto.text,
            conditionUrl = dayWeatherDto.conditionDto.iconUrl,
            date = dayDto.date.toCalendar()
        )

    }
)


private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.correctImageUrl() =
    "http:${this.replace(oldValue = "64x64", newValue = "128x128")}"