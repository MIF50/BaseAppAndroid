package com.mif50.baseapp.data.network

import com.mif50.baseapp.BuildConfig
import com.mif50.baseapp.helper.moshi.MoshiArrayListJsonAdapter
import com.mif50.baseapp.helper.moshi.MyStandardJsonAdapters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object Networking {

    private const val NETWORK_CALL_TIMEOUT = 60

    private fun getClient(
        cacheDir: File,
        cacheSize: Long
    ): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder().cache(Cache(cacheDir, cacheSize))

        httpClient.addInterceptor {
            val original = it.request()
            val request = original.newBuilder()
            it.proceed(request.build())
        }

        httpClient.addInterceptor {
            val request = it.request()
            it.proceed(
                request.newBuilder()
                    .url(request.url)
                    .build())
        }

        httpClient.addInterceptor(HttpLoggingInterceptor()
            .apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            })
            .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)

        return httpClient
    }

    fun create(
        baseUrl: String,
        cacheDir: File,
        cacheSize: Long
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getClient(cacheDir, cacheSize).build())
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    private fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(MyStandardJsonAdapters.FACTORY)
            .add(KotlinJsonAdapterFactory())
            .add(MoshiArrayListJsonAdapter.FACTORY)
            .build()
    }
}