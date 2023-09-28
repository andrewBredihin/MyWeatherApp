package com.bav.myweatherapp.data.util

import com.bav.myweatherapp.data.models.ForecastDay
import com.bav.myweatherapp.domain.util.ModelsConverter
import com.bav.myweatherapp.domain.models.ForecastDay as ForecastDayDomain

class ForecastDayConverter : ModelsConverter<ForecastDay, ForecastDayDomain> {
    override fun entityToModel(entity: ForecastDay): ForecastDayDomain {
        return ForecastDayDomain(
            date = entity.date,
            day = DayConverter().entityToModel(entity.day),
        )
    }
}
