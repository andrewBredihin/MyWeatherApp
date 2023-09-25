package com.bav.myweatherapp.presentation.di

import android.content.Context
import com.bav.myweatherapp.domain.usecase.GetWeatherNowUseCase
import com.bav.myweatherapp.presentation.ui.MainVMFactory
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainVMFactory(
        context: Context,
        getWeatherNowUseCase: GetWeatherNowUseCase,
    ): MainVMFactory {
        return MainVMFactory(
            context = context,
            getWeatherNowUseCase = getWeatherNowUseCase,
        )
    }
}
