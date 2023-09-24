package com.bav.myweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class Current(@SerializedName("temp_c") val tempC: String)
