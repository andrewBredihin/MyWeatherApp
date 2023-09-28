package com.bav.myweatherapp.presentation.weather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bav.myweatherapp.domain.usecase.GetWeatherNowUseCase
import com.bav.myweatherapp.domain.usecase.GetWeatherWeekUseCase

class MainVMFactory(
    private val getWeatherNowUseCase: GetWeatherNowUseCase,
    private val getWeatherWeekUseCase: GetWeatherWeekUseCase,
    private val context: Context,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            context = context,
            getWeatherNowUseCase = getWeatherNowUseCase,
            getWeatherWeekUseCase = getWeatherWeekUseCase,
        ) as T
    }
}
