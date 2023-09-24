package com.bav.myweatherapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bav.myweatherapp.data.retrofit.GetData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MainViewModel(private val retrofit: Retrofit) : ViewModel() {

    private val _temp = MutableStateFlow("0 C")
    val temp = _temp.asStateFlow()

    init {
        updateWeather()
    }

    fun updateWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weather = retrofit.create(GetData::class.java).getWeather()
                withContext(Dispatchers.Main) {
                    _temp.value = "${weather.current.tempC} \u2103"
                }
            } catch (e: Exception) {
                Log.e("MyTag", e.message.toString())
            }
        }
    }
}
