package com.bav.myweatherapp.data.util

import com.bav.myweatherapp.data.models.Current
import com.bav.myweatherapp.domain.util.ModelsConverter
import com.bav.myweatherapp.domain.models.Current as CurrentDomain

class CurrentConverter : ModelsConverter<Current, CurrentDomain> {
    override fun entityToModel(entity: Current): CurrentDomain {
        return CurrentDomain(
            tempC = entity.tempC,
            cloud = entity.cloud,
            wind = entity.wind,
            condition = ConditionConverter().entityToModel(entity.condition),
            precipitation = entity.precipitation,
        )
    }
}
