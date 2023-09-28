package com.bav.myweatherapp.domain.models

data class Day(
    val maxTemp: Double,
    val minTemp: Double,
    val avgTemp: Double,
    val maxWind: Double,
    val rainChance: Int,
    val snowChance: Int,
    val condition: Condition,
)
