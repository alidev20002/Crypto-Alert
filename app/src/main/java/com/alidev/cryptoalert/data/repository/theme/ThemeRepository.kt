package com.alidev.cryptoalert.data.repository.theme

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    fun readTheme(): Flow<Boolean>

    suspend fun writeTheme(value: Boolean)
}