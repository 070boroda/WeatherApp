package com.zelianko.weatherapp.presentation.favorite

import com.arkivanov.decompose.ComponentContext

class DefaultFavoriteComponen (
    componentContext: ComponentContext
): FavoriteComponent, ComponentContext by componentContext