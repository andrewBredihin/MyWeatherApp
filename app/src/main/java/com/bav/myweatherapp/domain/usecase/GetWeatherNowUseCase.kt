package com.bav.myweatherapp.domain.usecase

import com.bav.myweatherapp.domain.models.Weather
import com.bav.myweatherapp.domain.repository.WeatherRepository

class GetWeatherNowUseCase(private val repository: WeatherRepository) {

    suspend fun execute(city: String, lang: String): Weather {
        return repository.getWeatherNow(city = city, lang = lang)
    }
}
