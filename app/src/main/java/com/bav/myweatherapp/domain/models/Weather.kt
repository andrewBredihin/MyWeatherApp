package com.bav.myweatherapp.domain.models

data class Weather(
    val location: Location,
    val current: Current,
    val forecast: Forecast? = null
)
