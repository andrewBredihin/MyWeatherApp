package com.bav.myweatherapp.data.storage.network

import com.bav.myweatherapp.data.retrofit.GetData
import com.bav.myweatherapp.data.storage.WeatherStorage
import com.bav.myweatherapp.data.util.WeatherConverter
import com.bav.myweatherapp.domain.models.Weather
import retrofit2.Retrofit

class WeatherStorageNetwork(private val retrofit: Retrofit) : WeatherStorage {
    override suspend fun get(city: String, lang: String): Weather {
        val weather = retrofit.create(GetData::class.java).getWeather(city = city, lang = lang)
        return WeatherConverter().entityToModel(weather)
    }
}
