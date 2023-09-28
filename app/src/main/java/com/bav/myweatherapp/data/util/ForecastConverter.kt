package com.bav.myweatherapp.data.util

import com.bav.myweatherapp.data.models.Forecast
import com.bav.myweatherapp.domain.util.ModelsConverter
import com.bav.myweatherapp.domain.models.Forecast as ForecastDomain

class ForecastConverter : ModelsConverter<Forecast, ForecastDomain> {
    override fun entityToModel(entity: Forecast): ForecastDomain {
        return ForecastDomain(
            forecastDay = entity.forecastDay.map { forecastDay ->
                ForecastDayConverter().entityToModel(forecastDay)
            },
        )
    }
}
