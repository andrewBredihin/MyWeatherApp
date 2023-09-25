package com.bav.myweatherapp.domain.models

data class Current(
    val tempC: String,
    val cloud: Int,
    val wind: Double,
    val condition: Condition,
    val precipitation: Double,
)
