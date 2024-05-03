package com.zelianko.weatherapp.domain.usecase

import com.zelianko.weatherapp.domain.entity.City
import com.zelianko.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend fun addToFavourite(city: City) = repository.addToFavourite(city)

    suspend fun removeFromFavourite(cityId: Int) = repository.removeFromFavourite(cityId)
}