package com.bav.myweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: Day,
    @SerializedName("hour")
    val hours: List<Hour>,
)
