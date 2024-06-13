package com.zelianko.weatherapp.di

import android.content.Context
import com.zelianko.weatherapp.data.local.db.FavouriteDataBase
import com.zelianko.weatherapp.data.local.db.FavouritesCitiesDao
import com.zelianko.weatherapp.data.network.api.ApiFactory
import com.zelianko.weatherapp.data.network.api.ApiService
import com.zelianko.weatherapp.data.repository.FavoriteRepositoryImpl
import com.zelianko.weatherapp.data.repository.SearchRepositoryImpl
import com.zelianko.weatherapp.data.repository.WeatherRepositoryImpl
import com.zelianko.weatherapp.domain.repository.FavoriteRepository
import com.zelianko.weatherapp.domain.repository.SearchRepository
import com.zelianko.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[ApplicationScope Provides]
        fun provideFavouriteDataBase(context: Context): FavouriteDataBase {
            return FavouriteDataBase.getInstance(context)
        }

        @[ApplicationScope Provides]
        fun provideFavouriteCitiesDao(favouriteDataBase: FavouriteDataBase): FavouritesCitiesDao {
            return favouriteDataBase.favouriteCitiesDao()
        }
    }
}