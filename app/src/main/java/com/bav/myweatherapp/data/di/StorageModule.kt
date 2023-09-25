package com.bav.myweatherapp.data.di

import com.bav.myweatherapp.data.storage.WeatherStorage
import com.bav.myweatherapp.data.storage.network.WeatherStorageNetwork
import com.bav.myweatherapp.data.storage.test.WeatherStorageTest
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class StorageModule {

    @Provides
    @RepositoryModule.NetworkStorage
    fun provideWeatherStorageNetwork(retrofit: Retrofit): WeatherStorage {
        return WeatherStorageNetwork(retrofit = retrofit)
    }

    @Provides
    @RepositoryModule.TestStorage
    fun provideWeatherStorageTest(): WeatherStorage {
        return WeatherStorageTest()
    }
}
