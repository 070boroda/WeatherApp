package com.zelianko.weatherapp.data.repository

import com.zelianko.weatherapp.data.network.api.ApiService
import com.zelianko.weatherapp.domain.entity.City
import com.zelianko.weatherapp.domain.repository.SearchRepository
import com.zelianko.weatherapp.data.mapper.toEntities
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}