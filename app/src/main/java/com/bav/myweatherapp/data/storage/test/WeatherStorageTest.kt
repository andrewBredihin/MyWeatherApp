package com.bav.myweatherapp.data.storage.test

import com.bav.myweatherapp.data.storage.WeatherStorage
import com.bav.myweatherapp.domain.models.Condition
import com.bav.myweatherapp.domain.models.Current
import com.bav.myweatherapp.domain.models.Location
import com.bav.myweatherapp.domain.models.Weather

class WeatherStorageTest : WeatherStorage {
    override suspend fun get(city: String, lang: String): Weather {
        val location = Location(
            name = "Самара",
            localtime = "test 12:00",
        )
        val condition = Condition(
            text = "Облачно",
            code = 1114,
            icon = "test",
        )
        val current = Current(
            tempC = "20",
            cloud = 100,
            wind = 10.0,
            condition = condition,
            precipitation = 2.0,
        )
        return Weather(
            location = location,
            current = current,
        )
    }

    override suspend fun getWeek(city: String, lang: String, days: Int): Weather {
        TODO("Not yet implemented")
    }
}
