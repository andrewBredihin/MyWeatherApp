package com.bav.myweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_c")
    val maxTemp: Double,
    @SerializedName("mintemp_c")
    val minTemp: Double,
    @SerializedName("avgtemp_c")
    val avgTemp: Double,
    @SerializedName("maxwind_kph")
    val maxWind: Double,
    @SerializedName("daily_chance_of_rain")
    val rainChance: Int,
    @SerializedName("daily_chance_of_snow")
    val snowChance: Int,
    @SerializedName("condition")
    val condition: Condition,
)
