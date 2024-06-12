package com.zelianko.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.zelianko.weatherapp.data.network.api.ApiFactory
import com.zelianko.weatherapp.data.network.api.ApiService
import com.zelianko.weatherapp.presentation.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiFactory.apiService

        CoroutineScope(Dispatchers.Main).launch {
            val currentWeather = apiService.loadCurrentWeather("Minsk")
            val forecast = apiService.loadForecast("Minsk", 3)
            val city = apiService.searchCity("Minsk")
            Log.d("MainActivity",  "currentWeather $currentWeather\n" +
                    " forecast $forecast\n " +
                    " search $city" )
        }

        setContent {
            WeatherAppTheme {
                Text(text = "Hello")
            }
        }
    }
}
