package com.bav.myweatherapp.di

import com.bav.myweatherapp.domain.repository.WeatherRepository
import com.bav.myweatherapp.domain.usecase.GetWeatherNowUseCase
import com.bav.myweatherapp.domain.usecase.GetWeatherWeekUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class UseCaseModule {

    @Provides
    fun provideGetWeatherNowUseCase(repository: WeatherRepository): GetWeatherNowUseCase {
        return GetWeatherNowUseCase(repository = repository)
    }

    @Provides
    fun provideGetWeatherWeekUseCase(repository: WeatherRepository): GetWeatherWeekUseCase {
        return GetWeatherWeekUseCase(repository = repository)
    }
}
