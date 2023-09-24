package com.bav.myweatherapp.app

import android.app.Application
import com.bav.myweatherapp.di.AppComponent
import com.bav.myweatherapp.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this.applicationContext)
            .baseUrl(BASE_URL)
            .connectTimeout(CONNECT_TIMEOUT)
            .readTimeout(READ_TIMEOUT)
            .writeTimeout(WRITE_TIMEOUT)
            .build()
    }

    companion object {
        private const val BASE_URL = "https://api.weatherapi.com/v1/"
        private const val CONNECT_TIMEOUT = 5L
        private const val READ_TIMEOUT = 5L
        private const val WRITE_TIMEOUT = 5L
    }
}
