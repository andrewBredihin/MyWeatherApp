package com.bav.myweatherapp.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        @ConnectTimeout connectTimeout: Long,
        @ReadTimeout readTimeout: Long,
        @WriteTimeout writeTimeout: Long,
    ): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okBuilder.addInterceptor(interceptor)
        okBuilder.connectTimeout(connectTimeout, TimeUnit.MINUTES)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
        return okBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Qualifier
    annotation class BaseUrl

    @Qualifier
    annotation class ConnectTimeout

    @Qualifier
    annotation class ReadTimeout

    @Qualifier
    annotation class WriteTimeout
}
