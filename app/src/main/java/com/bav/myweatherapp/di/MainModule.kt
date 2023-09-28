package com.bav.myweatherapp.di

import android.content.Context
import com.bav.myweatherapp.domain.usecase.GetWeatherNowUseCase
import com.bav.myweatherapp.domain.usecase.GetWeatherWeekUseCase
import com.bav.myweatherapp.presentation.weather.MainVMFactory
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainVMFactory(
        context: Context,
        getWeatherNowUseCase: GetWeatherNowUseCase,
        getWeatherWeekUseCase: GetWeatherWeekUseCase,
    ): MainVMFactory {
        return MainVMFactory(
            context = context,
            getWeatherNowUseCase = getWeatherNowUseCase,
            getWeatherWeekUseCase = getWeatherWeekUseCase,
        )
    }
}
