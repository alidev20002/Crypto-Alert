package com.alidev.cryptoalert.data.datasource.theme

import com.alidev.cryptoalert.data.datastore.theme.ThemeDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeDataSourceDefault @Inject constructor(
    private val themeDataStore: ThemeDataStore
) : ThemeDataSource {

    override fun readTheme(): Flow<Boolean?> {
        return themeDataStore.readTheme()
    }

    override suspend fun writeTheme(value: Boolean) {
        themeDataStore.writeTheme(value)
    }
}