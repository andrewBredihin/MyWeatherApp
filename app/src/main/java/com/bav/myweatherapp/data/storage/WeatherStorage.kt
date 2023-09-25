package com.bav.myweatherapp.data.storage

import com.bav.myweatherapp.domain.models.Weather

interface WeatherStorage {

    suspend fun get(city: String, lang: String): Weather
}
