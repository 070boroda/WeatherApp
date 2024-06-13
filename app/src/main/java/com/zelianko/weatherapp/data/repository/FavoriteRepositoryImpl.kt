package com.zelianko.weatherapp.data.repository

import com.zelianko.weatherapp.data.local.db.FavouritesCitiesDao
import com.zelianko.weatherapp.data.mapper.toDbModel
import com.zelianko.weatherapp.data.mapper.toEntities
import com.zelianko.weatherapp.domain.entity.City
import com.zelianko.weatherapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favouritesCitiesDao: FavouritesCitiesDao
) : FavoriteRepository {
    override val favouriteCities: Flow<List<City>> = favouritesCitiesDao.getFavouriteCities()
        .map { it.toEntities() }

    override fun observeIsFavourite(cityId: Int): Flow<Boolean>  = favouritesCitiesDao.observeIsFavourite(cityId)

    override suspend fun addToFavourite(city: City) {
        favouritesCitiesDao.addFavourite(city.toDbModel())
    }

    override suspend fun removeFromFavourite(cityId: Int) {
        favouritesCitiesDao.deleteFromFavourite(cityId)
    }
}