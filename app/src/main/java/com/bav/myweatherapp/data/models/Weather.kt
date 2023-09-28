package com.bav.myweatherapp.data.models

data class Weather(
    val location: Location,
    val current: Current,
    val forecast: Forecast? = null
)
