package com.alidev.cryptoalert.data.datasource.theme

import kotlinx.coroutines.flow.Flow

interface ThemeDataSource {

    fun readTheme(): Flow<Boolean?>

    suspend fun writeTheme(value: Boolean)
}