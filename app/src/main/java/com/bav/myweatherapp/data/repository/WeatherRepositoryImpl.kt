package com.bav.myweatherapp.data.repository

import com.bav.myweatherapp.data.storage.WeatherStorage
import com.bav.myweatherapp.domain.models.Weather
import com.bav.myweatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val storage: WeatherStorage) : WeatherRepository {
    override suspend fun getWeatherNow(city: String, lang: String): Weather {
        return storage.get(city = city, lang = lang)
    }
}
