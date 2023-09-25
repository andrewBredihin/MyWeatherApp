package com.bav.myweatherapp.presentation.di

import android.content.Context
import com.bav.myweatherapp.presentation.ui.MainVMFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @Provides
    fun provideMainVMFactory(
        context: Context,
        retrofit: Retrofit,
    ): MainVMFactory {
        return MainVMFactory(
            context = context,
            retrofit = retrofit,
        )
    }
}
