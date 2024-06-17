package com.zelianko.weatherapp.presentation.favorite

import com.zelianko.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavoriteComponent {
    val model: StateFlow<FavoriteStore.State>

    fun onClickSearch()

    fun onClickFavourite()
    fun onCityItemClick(city: City)
}