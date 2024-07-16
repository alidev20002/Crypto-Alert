package com.alidev.cryptoalert.data.datastore.theme

import kotlinx.coroutines.flow.Flow

interface ThemeDataStore {

    fun readTheme(): Flow<Boolean?>

    suspend fun writeTheme(value: Boolean)
}