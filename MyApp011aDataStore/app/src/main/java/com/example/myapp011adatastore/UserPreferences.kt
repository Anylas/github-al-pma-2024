package com.example.myapp011adatastore
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Klíče a jméno DataStore
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

object UserPreferences {
    val NAME_KEY = stringPreferencesKey("name")
    val AGE_KEY = intPreferencesKey("age")
    val IS_ADULT_KEY = booleanPreferencesKey("isAdult")
}

suspend fun Context.saveUser(name: String, age: Int, isAdult: Boolean) {
    dataStore.edit { preferences ->
        preferences[UserPreferences.NAME_KEY] = name
        preferences[UserPreferences.AGE_KEY] = age
        preferences[UserPreferences.IS_ADULT_KEY] = isAdult
    }
}

fun Context.getUser(): Flow<User> {
    return dataStore.data.map { preferences ->
        val name = preferences[UserPreferences.NAME_KEY] ?: ""
        val age = preferences[UserPreferences.AGE_KEY] ?: 0
        val isAdult = preferences[UserPreferences.IS_ADULT_KEY] ?: false
        User(name, age, isAdult)
    }
}
// Model pro uchovávání dat
data class User(val name: String, val age: Int, val isAdult: Boolean)

