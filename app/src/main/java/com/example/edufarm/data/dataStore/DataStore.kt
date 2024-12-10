package com.example.edufarm.data.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

suspend fun saveToken(context: Context, email: String, token: String) {
    context.dataStore.edit { preferences ->
        preferences[stringPreferencesKey("auth_token_$email")] = token
    }
    Log.d("Auth", "Token berhasil disimpan untuk $email: $token")
}

suspend fun getToken(context: Context, email: String): String? {
    return context.dataStore.data
        .map { preferences -> preferences[stringPreferencesKey("auth_token_$email")] }
        .firstOrNull()
}


suspend fun clearToken(context: Context, email: String) {
    context.dataStore.edit { preferences ->
        preferences.remove(stringPreferencesKey("auth_token_$email"))
    }
    Log.d("Auth", "Token untuk $email dihapus.")
}


private val CURRENT_USER_EMAIL_KEY = stringPreferencesKey("current_user_email")

suspend fun saveCurrentUserEmail(context: Context, email: String) {
    context.dataStore.edit { preferences ->
        preferences[CURRENT_USER_EMAIL_KEY] = email
    }
    Log.d("Auth", "Email pengguna aktif disimpan: $email")
}

suspend fun getCurrentUserEmail(context: Context): String? {
    return context.dataStore.data
        .map { preferences -> preferences[CURRENT_USER_EMAIL_KEY] }
        .firstOrNull()
}


