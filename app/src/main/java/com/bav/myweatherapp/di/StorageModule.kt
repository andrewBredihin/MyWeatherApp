package com.bav.myweatherapp.di

import com.bav.myweatherapp.data.retrofit.GetData
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
    fun provideWeatherStorageNetwork(getData: GetData): WeatherStorage {
        return WeatherStorageNetwork(getData = getData)
    }

    @Provides
    @RepositoryModule.TestStorage
    fun provideWeatherStorageTest(): WeatherStorage {
        return WeatherStorageTest()
    }
}
