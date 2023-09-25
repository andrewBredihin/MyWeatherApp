package com.bav.myweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp_c")
    val tempC: String,
    @SerializedName("cloud")
    val cloud: Int,
    @SerializedName("wind_kph")
    val wind: Double,
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("precip_mm")
    val precipitation: Double,
)
