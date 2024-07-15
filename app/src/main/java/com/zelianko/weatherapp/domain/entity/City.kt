package com.zelianko.weatherapp.domain.entity

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Int,
    val name: String,
    val country: String
): Parcelable
