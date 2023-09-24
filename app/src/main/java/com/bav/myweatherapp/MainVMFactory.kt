package com.bav.myweatherapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit

class MainVMFactory(
    private val retrofit: Retrofit,
    private val context: Context,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            context = context,
            retrofit = retrofit,
        ) as T
    }
}
