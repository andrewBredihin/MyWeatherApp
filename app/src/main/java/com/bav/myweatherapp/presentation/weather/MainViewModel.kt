package com.bav.myweatherapp.presentation.weather

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bav.myweatherapp.R
import com.bav.myweatherapp.domain.models.ForecastDay
import com.bav.myweatherapp.domain.usecase.GetWeatherNowUseCase
import com.bav.myweatherapp.domain.usecase.GetWeatherWeekUseCase
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getWeatherNowUseCase: GetWeatherNowUseCase,
    private val getWeatherWeekUseCase: GetWeatherWeekUseCase,
    private val context: Context,
) : ViewModel() {
    // Погода в данный момент

    private val _temp = MutableStateFlow("0 C")
    val temp = _temp.asStateFlow()

    private val _city = MutableStateFlow("")
    val city = _city.asStateFlow()

    private val _time = MutableStateFlow("")
    val time = _time.asStateFlow()

    private val _wind = MutableStateFlow("0 м/с")
    val wind = _wind.asStateFlow()

    private val _backgroundImage = MutableStateFlow(
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.placeholder,
        ).asImageBitmap(),
    )
    val backgroundImage = _backgroundImage.asStateFlow()

    private val _condition = MutableStateFlow("Ясно")
    val condition = _condition.asStateFlow()

    private val _weather = MutableStateFlow(1000)
    val weather = _weather.asStateFlow()

    private val _clouds = MutableStateFlow(CloudsEnum.ZERO)
    val clouds = _clouds.asStateFlow()

    // Погода на неделю
    private val _week = MutableStateFlow<List<ForecastDay>>(emptyList())
    val week = _week.asStateFlow()

    private val _state = MutableStateFlow(false)
    val state = _state.asStateFlow()

    init {
        updateWeather()
    }

    fun updateWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                _state.value = true
                updateWeatherNow()
                delay(1000)
                _state.value = false
                delay(DELAY)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                updateWeatherWeek()
                delay(DELAY)
            }
        }
    }

    private suspend fun updateWeatherNow() {
        try {
            val weather = getWeatherNowUseCase.execute(city = "Самара", lang = "ru")
            withContext(Dispatchers.Main) {
                _temp.value = "${weather.current.tempC} \u2103"
                _city.value = weather.location.name
                _time.value = weather.location.localtime.split(" ")[1]
                _condition.value = weather.current.condition.text

                val wind = String.format("%.1f", weather.current.wind / 3.6)
                _wind.value = "$wind м/с"

                _clouds.value = when (weather.current.cloud) {
                    in 0..25 -> CloudsEnum.ZERO
                    in 26..50 -> CloudsEnum.MIN
                    in 51..75 -> CloudsEnum.MEDIUM
                    else -> CloudsEnum.MAX
                }

                _weather.value = weather.current.condition.code
                when (weather.current.condition.code) {
                    in 1000..1030 -> loadBackgroundImage(CLEAR_SKY)

                    in 1135..1147 -> loadBackgroundImage(FOG) // туман

                    1063 -> loadBackgroundImage(RAIN) // дождь
                    in 1180..1201 -> loadBackgroundImage(RAIN) // дождь
                    1237 -> loadBackgroundImage(RAIN) // ледяной дождь
                    in 1261..1264 -> loadBackgroundImage(RAIN) // ледяной дождь
                    in 1240..1246 -> loadBackgroundImage(SHOWER) // ливень

                    1066 -> loadBackgroundImage(SNOW) // снег
                    1072 -> loadBackgroundImage(SNOW) // морось
                    in 1150..1171 -> loadBackgroundImage(SNOW) // морось
                    in 1210..1225 -> loadBackgroundImage(SNOW) // снег
                    in 1255..1258 -> loadBackgroundImage(SNOW) // снег
                    in 1114..1117 -> loadBackgroundImage(BLIZZARD) // метель

                    1069 -> loadBackgroundImage(SNOW_AND_RAIN) // снег c дождем
                    in 1204..1207 -> loadBackgroundImage(SNOW_AND_RAIN) // дождь со снегом
                    in 1249..1252 -> loadBackgroundImage(SHOWER) // ливень со снегом

                    1087 -> loadBackgroundImage(STORM) // гроза
                    in 1273..1276 -> loadBackgroundImage(STORM) // дождь с грозой
                    in 1279..1282 -> loadBackgroundImage(STORM) // снег с грозой
                }
            }
        } catch (e: Exception) {
            Log.e("MyTag", e.message.toString())
        }
    }

    private suspend fun updateWeatherWeek() {
        try {
            val forecast = getWeatherWeekUseCase.execute(city = "Самара", lang = "ru", days = 7)
            withContext(Dispatchers.Main) {
                _week.value = forecast.forecast?.forecastDay.orEmpty()
            }
        } catch (e: Exception) {
            Log.e("MyTag", e.message.toString())
        }
    }

    private fun loadBackgroundImage(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(path)
                .error(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.placeholder,
                    ),
                )
                .submit()
                .get()
                .asImageBitmap()

            withContext(Dispatchers.Main) {
                _backgroundImage.value = bitmap
            }
        }
    }

    companion object {
        const val DELAY = 15000L

        const val CLEAR_SKY =
            "https://img.freepik.com/premium-photo/blue-sky-with-light-white-clouds-perfect-vertical-background-with-large-copy-space_483040-1474.jpg"
        const val CLEAR_SKY_WINTER =
            "https://images.wallpaperscraft.ru/image/single/moroz_uzory_inej_9685_225x300.jpg"
        const val RAIN =
            "https://img2.akspic.ru/previews/5/8/4/8/4/148485/148485-voda-dozhd-sinij-nebo-surface-x750.jpg"
        const val SHOWER =
            "https://cppd.ask.fm/fba/90f43/07ea/46e5/a073/a68666e4c5a8/thumb/13815.jpg"
        const val STORM =
            "https://w.forfun.com/fetch/f5/f5ac14aa1b4345f5876a2ef3c8827bd5.jpeg"
        const val SNOW =
            "https://w.forfun.com/fetch/21/21a22847b7c5c5248432843fd1d1b402.jpeg"
        const val BLIZZARD =
            "https://stihi.ru/pics/2022/02/26/1599.jpg"
        const val SNOW_AND_RAIN =
            "https://img1.goodfon.ru/original/360x480/5/34/zima-doroga-sneg-priroda-6243.jpg"
        const val FOG =
            "https://priroda.club/uploads/posts/2022-09/1662696211_27-priroda-club-p-krasivii-tuman-krasivo-foto-28.jpg"
    }

    enum class CloudsEnum {
        ZERO,
        MIN,
        MEDIUM,
        MAX,
    }
}
