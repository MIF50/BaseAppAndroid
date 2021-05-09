package com.mif50.baseapp.data.local.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPrefStorage @Inject constructor(
    @ApplicationContext val context: Context
):PrefStorage {

    companion object {
        private const val KEY_PREF_NAME = "app_preferences"
    }

    private val Context.dataStore by preferencesDataStore(KEY_PREF_NAME)

    override val isDarkTheme: Flow<Boolean>
        get() = context.dataStore.getValueAsFlow(Keys.IS_DARK_THEME, false)

    override suspend fun setIsDarkTheme(isDarkTheme: Boolean) {
        context.dataStore.setValue(Keys.IS_DARK_THEME, isDarkTheme)
    }





    override suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }

    //Todo: Keys
    private object Keys {
        val IS_DARK_THEME = booleanPreferencesKey("pref_dark_theme",)
        val NAME = stringPreferencesKey("pref_name")
    }

    /***
     * handy function to save key-value pairs in Preference. Sets or updates the value in Preference
     * @param key used to identify the preference
     * @param value the value to be saved in the preference
     */
    private suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        this.edit { preferences ->
            // save the value in prefs
            preferences[key] = value
        }
    }

    /***
     * handy function to return Preference value based on the Preference key
     * @param key  used to identify the preference
     * @param defaultValue value in case the Preference does not exists
     * @throws Exception if there is some error in getting the value
     * @return [Flow] of [T]
     */
    private fun <T> DataStore<Preferences>.getValueAsFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return this.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                // we try again to store the value in the map operator
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // return the default value if it doesn't exist in the storage
            preferences[key] ?: defaultValue
        }
    }
}