package com.bav.myweatherapp.di

import android.content.Context
import com.bav.myweatherapp.MainActivity
import com.bav.myweatherapp.data.di.NetworkModule
import com.bav.myweatherapp.presentation.di.MainModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        MainModule::class,
        NetworkModule::class,
    ],
)
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun baseUrl(@NetworkModule.BaseUrl baseUrl: String): Builder

        @BindsInstance
        fun connectTimeout(@NetworkModule.ConnectTimeout connectTimeout: Long): Builder

        @BindsInstance
        fun readTimeout(@NetworkModule.ReadTimeout readTimeout: Long): Builder

        @BindsInstance
        fun writeTimeout(@NetworkModule.WriteTimeout writeTimeout: Long): Builder

        fun build(): AppComponent
    }
}
