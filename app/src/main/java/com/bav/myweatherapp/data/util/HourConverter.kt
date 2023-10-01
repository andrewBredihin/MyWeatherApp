package com.bav.myweatherapp.data.util

import com.bav.myweatherapp.data.models.Hour
import com.bav.myweatherapp.domain.util.ModelsConverter
import com.bav.myweatherapp.domain.models.Hour as HourDomain

class HourConverter : ModelsConverter<Hour, HourDomain> {
    override fun entityToModel(entity: Hour): HourDomain {
        return HourDomain(
            time = entity.time,
            temp = entity.temp,
            wind = entity.temp,
            condition = entity.condition,
        )
    }
}
