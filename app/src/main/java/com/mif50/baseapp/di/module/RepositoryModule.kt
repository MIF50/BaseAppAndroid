package com.mif50.baseapp.di.module

import com.mif50.baseapp.data.network.ApiService
import com.mif50.baseapp.domain.repository.ErrorRepository
import com.mif50.baseapp.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTransactionRepository(api: ApiService) = TransactionRepository(api)


    @Singleton
    @Provides
    fun provideErrorRepository(api: ApiService) = ErrorRepository(api)



}