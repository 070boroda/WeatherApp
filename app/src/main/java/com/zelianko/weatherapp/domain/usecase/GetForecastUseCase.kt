package com.zelianko.weatherapp.domain.usecase

import com.zelianko.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityId: Int) =  repository.getForecast(cityId)
}