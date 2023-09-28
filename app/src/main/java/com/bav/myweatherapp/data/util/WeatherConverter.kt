package com.bav.myweatherapp.data.util

import com.bav.myweatherapp.data.models.Weather
import com.bav.myweatherapp.domain.util.ModelsConverter
import com.bav.myweatherapp.domain.models.Weather as WeatherDomain

class WeatherConverter : ModelsConverter<Weather, WeatherDomain> {
    override fun entityToModel(entity: Weather): WeatherDomain {
        return WeatherDomain(
            location = LocationConverter().entityToModel(entity.location),
            current = CurrentConverter().entityToModel(entity.current),
            forecast = entity.forecast?.let { ForecastConverter().entityToModel(it) },
        )
    }
}
