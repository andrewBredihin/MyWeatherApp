package com.bav.myweatherapp.presentation.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bav.myweatherapp.domain.usecase.GetWeatherNowUseCase

class MainVMFactory(
    private val getWeatherNowUseCase: GetWeatherNowUseCase,
    private val context: Context,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            context = context,
            getWeatherNowUseCase = getWeatherNowUseCase,
        ) as T
    }
}
