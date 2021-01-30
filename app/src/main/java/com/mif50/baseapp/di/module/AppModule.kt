package com.mif50.baseapp.di.module

import android.content.Context
import com.mif50.baseapp.BuildConfig
import com.mif50.baseapp.data.network.ApiService
import com.mif50.baseapp.data.network.Networking
import com.mif50.baseapp.domain.repository.ITransactionRepository
import com.mif50.baseapp.domain.repository.TransactionRepository
import com.mif50.baseapp.domain.usecase.GetTransactionUseCase
import com.mif50.baseapp.helper.network.NetworkHelper
import com.mif50.baseapp.helper.rx.RxSchedulerProvider
import com.mif50.baseapp.helper.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(
            baseUrl: String,
            @ApplicationContext context: Context
    ) = Networking.create(
            baseUrl,
            context.cacheDir,
            10 * 1024 * 1024 // 10MB
    )

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService {
       return retrofit.build().create(ApiService::class.java)
    }
}
