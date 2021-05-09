package com.mif50.baseapp.di.module

import com.mif50.baseapp.data.local.pref.AppPrefStorage
import com.mif50.baseapp.data.local.pref.PrefStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun providesPreferenceStorage(
        appPreferenceStorage: AppPrefStorage
    ): PrefStorage
}
