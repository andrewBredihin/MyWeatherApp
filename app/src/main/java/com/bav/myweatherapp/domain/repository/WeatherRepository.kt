package com.bav.myweatherapp.domain.repository

import com.bav.myweatherapp.domain.models.Weather

interface WeatherRepository {

    suspend fun getWeatherNow(city: String, lang: String): Weather
}
