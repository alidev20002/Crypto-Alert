package com.alidev.cryptoalert.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class KeyValueBase(
    private val context: Context,
    fileName: String
) : KeyValueInterface {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = fileName)

    override fun readString(key: String): Flow<String?> {
        val value = context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }

        return value
    }

    override suspend fun writeString(key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override fun readStringSync(key: String): String? {
        return runBlocking(Dispatchers.IO) {
            readString(key).first()
        }
    }

    override fun writeStringSync(key: String, value: String) {
        runBlocking(Dispatchers.IO) {
            writeString(key, value)
        }
    }
}