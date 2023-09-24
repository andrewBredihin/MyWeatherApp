package com.bav.myweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit

class MainVMFactory(private val retrofit: Retrofit) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            retrofit = retrofit,
        ) as T
    }
}
