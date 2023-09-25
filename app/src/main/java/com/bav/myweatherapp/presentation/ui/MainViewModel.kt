package com.bav.myweatherapp.presentation.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bav.myweatherapp.R
import com.bav.myweatherapp.data.retrofit.GetData
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.lang.Math.floor

class MainViewModel(
    private val retrofit: Retrofit,
    private val context: Context,
) : ViewModel() {

    private val _temp = MutableStateFlow("0 C")
    val temp = _temp.asStateFlow()

    private val _city = MutableStateFlow("")
    val city = _city.asStateFlow()

    private val _time = MutableStateFlow("")
    val time = _time.asStateFlow()

    private val _wind = MutableStateFlow("")
    val wind = _wind.asStateFlow()

    private val _backgroundImage = MutableStateFlow(
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.placeholder,
        ).asImageBitmap(),
    )
    val backgroundImage = _backgroundImage.asStateFlow()

    private val _condition = MutableStateFlow("")
    val condition = _condition.asStateFlow()

    private val _rain = MutableStateFlow(PrecipitationEnum.MIN)
    val rain = _rain.asStateFlow()

    init {
        updateWeather()
    }

    fun updateWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weather = retrofit.create(GetData::class.java).getWeather("Самара", "ru")
                withContext(Dispatchers.Main) {
                    _temp.value = "${weather.current.tempC} \u2103"
                    _city.value = weather.location.name
                    _time.value = weather.location.localtime.split(" ")[1]
                    _condition.value = weather.current.condition.text

                    val wind = kotlin.math.floor(weather.current.wind / 3.6 * 10.0) / 10.0
                    _wind.value = "$wind м/с"

                    _rain.value = when ((weather.current.precipitation * 10).toInt()) {
                        in 0..9 -> PrecipitationEnum.MIN
                        in 10..29 -> PrecipitationEnum.MEDIUM
                        else -> PrecipitationEnum.MAX
                    }

                    val path = when (weather.current.cloud) {
                        in 0..25 -> CLOUD_ZERO
                        in 26..50 -> CLOUD_MIN
                        in 51..75 -> CLOUD_MEDIUM
                        else -> CLOUD_MAX
                    }
                    loadBackgroundImage(path)
                }
            } catch (e: Exception) {
                Log.e("MyTag", e.message.toString())
            }
        }
    }

    private fun loadBackgroundImage(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(path)
                .submit()
                .get()
                .asImageBitmap()

            withContext(Dispatchers.Main) {
                _backgroundImage.value = bitmap
            }
        }
    }

    companion object {
        const val CLOUD_ZERO =
            "https://img.freepik.com/premium-photo/blue-sky-with-light-white-clouds-perfect-vertical-background-with-large-copy-space_483040-1474.jpg"
        const val CLOUD_MIN =
            "https://img.freepik.com/premium-photo/blue-sky-with-white-clouds-vertical-background-with-space-ror-your-own-text_483040-156.jpg"
        const val CLOUD_MEDIUM =
            "https://img.freepik.com/premium-photo/day-sky-with-clouds-vertical-photo_182793-602.jpg?"
        const val CLOUD_MAX =
            "https://img.freepik.com/premium-photo/sky-with-clouds-may-be-used-as-background-vertical_483040-4941.jpg"
    }

    enum class PrecipitationEnum {
        MIN,
        MEDIUM,
        MAX,
    }
}
