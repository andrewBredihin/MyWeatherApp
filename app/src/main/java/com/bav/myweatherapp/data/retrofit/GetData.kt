package com.bav.myweatherapp.data.retrofit

import com.bav.myweatherapp.data.models.Weather
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "95f22bbb6280437ca1d72511232409"

interface GetData {

    @GET("current.json?key=$API_KEY")
    suspend fun getWeather(@Query("q") city: String, @Query("lang") lang: String): Weather
}
