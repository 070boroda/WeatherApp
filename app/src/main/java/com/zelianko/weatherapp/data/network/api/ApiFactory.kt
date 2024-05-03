package com.zelianko.weatherapp.data.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//1b5fde6abc9d403aac7123011240305
object ApiFactory {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create()
}