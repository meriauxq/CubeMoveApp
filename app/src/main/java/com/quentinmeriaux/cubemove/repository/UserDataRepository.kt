package com.quentinmeriaux.cubemove.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.quentinmeriaux.cubemove.model.ThemePreference
import com.quentinmeriaux.cubemove.model.UserData
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserDataRepository(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val THEME: Preferences.Key<String> = stringPreferencesKey("THEME_PREFERENCE")
    }

    val userData = dataStore.data.map {
        UserData(
            themePreference = when (it[THEME]) {
                ThemePreference.LIGHT.name -> ThemePreference.LIGHT
                ThemePreference.DARK.name -> ThemePreference.DARK
                else -> ThemePreference.SYSTEM_DEFAULT
            }
        )
    }

    suspend fun setThemePreference(themePreference: ThemePreference) {
        dataStore.edit { settings ->
            settings[THEME] = themePreference.name
        }
    }
}