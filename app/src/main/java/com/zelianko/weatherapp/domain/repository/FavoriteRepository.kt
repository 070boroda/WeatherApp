package com.zelianko.weatherapp.domain.repository

import com.zelianko.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    val favouriteCities: Flow<City>
    fun observeIsFavourite(cityId: Int): Flow<Boolean>
    suspend fun addToFavourite(city: City)
    suspend fun removeFromFavourite(cityId: Int)
}