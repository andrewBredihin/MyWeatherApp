package com.bav.myweatherapp.data.util

import com.bav.myweatherapp.data.models.Condition
import com.bav.myweatherapp.domain.util.ModelsConverter
import com.bav.myweatherapp.domain.models.Condition as ConditionDomain

class ConditionConverter : ModelsConverter<Condition, ConditionDomain> {
    override fun entityToModel(entity: Condition): ConditionDomain {
        return ConditionDomain(
            text = entity.text,
            code = entity.code,
        )
    }
}
