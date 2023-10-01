package com.bav.myweatherapp.data.models

import com.bav.myweatherapp.domain.models.Condition
import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("time")
    val time: String,
    @SerializedName("temp_c")
    val temp: Double,
    @SerializedName("wind_kph")
    val wind: Double,
    @SerializedName("condition")
    val condition: Condition,
)
