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

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as WeatherApp).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContent {
            RootContent(
                component = rootContentFactory.create(
                    defaultComponentContext()
                )
            )
        }
    }
}
