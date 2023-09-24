package com.bav.myweatherapp.data.retrofit

import com.bav.myweatherapp.data.models.Weather
import retrofit2.http.GET

const val API_KEY = "95f22bbb6280437ca1d72511232409"

interface GetData {

    @GET("current.json?key=$API_KEY&q=Samara")
    suspend fun getWeather(): Weather
}
