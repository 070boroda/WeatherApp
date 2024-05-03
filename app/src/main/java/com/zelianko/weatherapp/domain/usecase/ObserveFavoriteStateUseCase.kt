package com.zelianko.weatherapp.domain.usecase

import com.zelianko.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class ObserveFavoriteStateUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(cityId: Int) = repository.observeIsFavourite(cityId)
}