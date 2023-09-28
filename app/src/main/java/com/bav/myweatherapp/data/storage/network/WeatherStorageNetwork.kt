package com.bav.myweatherapp.data.storage.network

import com.bav.myweatherapp.data.retrofit.GetData
import com.bav.myweatherapp.data.storage.WeatherStorage
import com.bav.myweatherapp.data.util.WeatherConverter
import com.bav.myweatherapp.domain.models.Weather

class WeatherStorageNetwork(private val getData: GetData) : WeatherStorage {
    override suspend fun get(city: String, lang: String): Weather {
        val weather = getData.getWeather(city = city, lang = lang)
        return WeatherConverter().entityToModel(weather)
    }

    override suspend fun getWeek(city: String, lang: String, days: Int): Weather {
        val weather = getData.getWeatherWeek(city = city, lang = lang, days = days)
        return WeatherConverter().entityToModel(weather)
    }
}
