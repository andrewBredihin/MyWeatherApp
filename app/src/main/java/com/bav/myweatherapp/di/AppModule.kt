package com.bav.myweatherapp.di

import com.bav.myweatherapp.MainVMFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AppModule {

    @Provides
    fun provideMainVMFactory(retrofit: Retrofit): MainVMFactory {
        return MainVMFactory(retrofit = retrofit)
    }
}
