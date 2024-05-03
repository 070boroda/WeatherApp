package com.zelianko.weatherapp.domain.usecase

import com.zelianko.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke() = repository.favouriteCities
}