package com.bav.myweatherapp.data.util

import com.bav.myweatherapp.data.models.Location
import com.bav.myweatherapp.domain.util.ModelsConverter
import com.bav.myweatherapp.domain.models.Location as LocationDomain

class LocationConverter : ModelsConverter<Location, LocationDomain> {
    override fun entityToModel(entity: Location): LocationDomain {
        return LocationDomain(
            name = entity.name,
            localtime = entity.localtime,
        )
    }
}
