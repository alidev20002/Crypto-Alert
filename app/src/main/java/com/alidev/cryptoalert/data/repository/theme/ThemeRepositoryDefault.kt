package com.alidev.cryptoalert.data.repository.theme

import com.alidev.cryptoalert.data.datasource.theme.ThemeDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeRepositoryDefault @Inject constructor(
    private val themeDataSource: ThemeDataSource
) : ThemeRepository {

    override fun readTheme(): Flow<Boolean> {
        return themeDataSource.readTheme().map {
            it ?: false
        }
    }

    override suspend fun writeTheme(value: Boolean) {
        themeDataSource.writeTheme(value)
    }
}