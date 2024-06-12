package com.zelianko.weatherapp.data.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//1b5fde6abc9d403aac7123011240305
object ApiFactory {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {chain ->
            val originalRequest = chain.request()
            val newUrl = originalRequest.url().newBuilder()
                .addQueryParameter("key", "1b5fde6abc9d403aac7123011240305")
                .build()
            val newRequest =  originalRequest.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)

        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create()
}