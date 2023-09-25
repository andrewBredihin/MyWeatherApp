package com.bav.myweatherapp.domain.util

interface ModelsConverter<Entity, Model> {

    fun entityToModel(entity: Entity): Model
}
