package com.bav.myweatherapp.data.di

import com.bav.myweatherapp.data.repository.WeatherRepositoryImpl
import com.bav.myweatherapp.data.storage.WeatherStorage
import com.bav.myweatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module(includes = [StorageModule::class])
class RepositoryModule {

    @Provides
    fun provideWeatherRepository(@NetworkStorage storage: WeatherStorage): WeatherRepository {
        return WeatherRepositoryImpl(storage = storage)
    }

    @Qualifier
    annotation class NetworkStorage

    @Qualifier
    annotation class TestStorage
}
