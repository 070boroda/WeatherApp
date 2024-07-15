package com.zelianko.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.zelianko.weatherapp.WeatherApp
import com.zelianko.weatherapp.domain.usecase.ChangeFavouriteStateUseCase
import com.zelianko.weatherapp.domain.usecase.SearchCityUseCase
import com.zelianko.weatherapp.presentation.root.DefaultRootComponent
import com.zelianko.weatherapp.presentation.root.RootContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {


    @Inject
    lateinit var rootContentFactory: DefaultRootComponent.Factory

//    @Inject
//    lateinit var searchCityUseCase: SearchCityUseCase
//
//    @Inject
//    lateinit var changeFavouriteStateUseCase: ChangeFavouriteStateUseCase


    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as WeatherApp).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

//        val scope = CoroutineScope(Dispatchers.IO)
//
//        scope.launch {
//            searchCityUseCase.invoke("Пон").forEach {
//
//                changeFavouriteStateUseCase.addToFavourite(it);
//            }
//
//        }
//
//        val apiService = ApiFactory.apiService
//        CoroutineScope(Dispatchers.Main).launch {
//            val currentWeather = apiService.loadCurrentWeather("Minsk")
//            val forecast = apiService.loadForecast("Minsk", 3)
//            val city = apiService.searchCity("Minsk")
//            Log.d("MainActivity",  "currentWeather $currentWeather\n" +
//                    " forecast $forecast\n " +
//                    " search $city" )
//        }

        setContent {
            RootContent(
                component = rootContentFactory.create(
                    defaultComponentContext()
                )
            )
        }
    }
}
