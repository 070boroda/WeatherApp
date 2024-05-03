package com.zelianko.weatherapp.domain.repository

import com.zelianko.weatherapp.domain.entity.City

interface SearchRepository {

    suspend fun search(query: String): List<City>
}