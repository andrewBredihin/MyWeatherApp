package com.bav.myweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastDay>,
)
