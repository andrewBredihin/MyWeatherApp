package com.bav.myweatherapp

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bav.myweatherapp.data.retrofit.GetData
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

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

    private val _image = MutableStateFlow(
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.placeholder,
        ).asImageBitmap(),
    )
    val image = _image.asStateFlow()

    init {
        updateWeather()
    }

    fun updateWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weather = retrofit.create(GetData::class.java).getWeather("Samara", "ru")
                withContext(Dispatchers.Main) {
                    _temp.value = "${weather.current.tempC} \u2103"
                    _city.value = weather.location.name
                    _time.value = weather.location.localtime.split(" ")[1]

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
                _image.value = bitmap
            }
        }
    }

    companion object {
        const val CLOUD_ZERO =
            "https://cloud.pulse19.ru/uploads/2021/04/foto-pixabay.com_-3-2048x1365.jpg"
        const val CLOUD_MIN =
            "https://get.pxhere.com/photo/horizon-light-cloud-sky-sun-sunlight-ray-atmosphere-summer-daytime-tranquility-flight-peace-cumulus-italy-blue-blue-sky-clouds-relaxation-hot-serenity-rays-of-sunshine-the-form-of-clouds-meteorological-phenomenon-atmosphere-of-earth-708918.jpg"
        const val CLOUD_MEDIUM =
            "https://wpsovet.ru/wp-content/uploads/a/9/f/a9ffb2d81358da630d7d9ff2c50fba70.jpeg"
        const val CLOUD_MAX =
            "https://get.pxhere.com/photo/nature-horizon-light-cloud-sky-sunlight-cloudy-time-atmosphere-daytime-cumulus-blue-clouds-meteorological-phenomenon-atmosphere-of-earth-1073715.jpg"
    }
}
