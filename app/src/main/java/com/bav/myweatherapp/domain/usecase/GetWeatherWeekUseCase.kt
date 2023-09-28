package com.bav.myweatherapp.domain.usecase

import com.bav.myweatherapp.domain.models.Weather
import com.bav.myweatherapp.domain.repository.WeatherRepository

class GetWeatherWeekUseCase(private val repository: WeatherRepository) {

    suspend fun execute(city: String, lang: String, days: Int): Weather {
        return repository.getWeatherWeek(city = city, lang = lang, days = days)
    }
}
