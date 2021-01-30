package com.mif50.baseapp.di.module

import com.mif50.baseapp.domain.repository.ErrorRepository
import com.mif50.baseapp.domain.repository.TransactionRepository
import com.mif50.baseapp.domain.usecase.GetErrorUseCase
import com.mif50.baseapp.domain.usecase.GetTransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetTransactionUseCase(repository: TransactionRepository) = GetTransactionUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideGetErrorUseCase(repository: ErrorRepository) = GetErrorUseCase(repository = repository)
}