package com.mif50.baseapp.data.local.pref

import kotlinx.coroutines.flow.Flow

interface PrefStorage {
    val isDarkTheme: Flow<Boolean>
    suspend fun setIsDarkTheme(isDarkTheme: Boolean)

    /***
     * clears all the stored data
     */
    suspend fun clearPreferenceStorage()
}