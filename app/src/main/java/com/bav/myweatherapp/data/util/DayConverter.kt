package com.bav.myweatherapp.data.util

import com.bav.myweatherapp.data.models.Day
import com.bav.myweatherapp.domain.util.ModelsConverter
import com.bav.myweatherapp.domain.models.Day as DayDomain

class DayConverter : ModelsConverter<Day, DayDomain> {
    override fun entityToModel(entity: Day): DayDomain {
        return DayDomain(
            maxTemp = entity.maxTemp,
            minTemp = entity.minTemp,
            avgTemp = entity.avgTemp,
            maxWind = entity.maxWind,
            rainChance = entity.rainChance,
            snowChance = entity.snowChance,
            condition = ConditionConverter().entityToModel(entity.condition),
        )
    }
}
