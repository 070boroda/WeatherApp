package com.zelianko.weatherapp.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.zelianko.weatherapp.presentation.details.DetailsComponent
import com.zelianko.weatherapp.presentation.favorite.FavoriteComponent
import com.zelianko.weatherapp.presentation.search.SearchComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Favourite(val component: FavoriteComponent) : Child
        data class Search(val component: SearchComponent) : Child
        data class Detail(val component: DetailsComponent) : Child
    }
}