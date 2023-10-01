package com.bav.myweatherapp.domain.models

data class Hour(
    val time: String,
    val temp: Double,
    val wind: Double,
    val condition: Condition,
)
