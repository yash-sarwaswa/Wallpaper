package com.example.wallpaper.di.module

import android.app.WallpaperManager
import android.content.Context
import com.example.wallpaper.MyApplication
import com.example.wallpaper.network.ApiKeyInterceptor
import com.example.wallpaper.network.NetworkService
import com.example.wallpaper.utils.BASE_URL
import com.example.wallpaper.utils.DownloadMangerUtil

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModule {

    fun provideContext(application: MyApplication): Context {
        return application
    }

    fun provideDownloadManagerUtils(context: Context): DownloadMangerUtil{
        return DownloadMangerUtil(context)
    }

    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    fun provideWallpaperManager(context: Context): WallpaperManager {
        return WallpaperManager.getInstance(context)
    }

}