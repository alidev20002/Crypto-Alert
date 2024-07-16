package com.alidev.cryptoalert.data.datastore.theme

import android.content.Context
import com.alidev.cryptoalert.data.datastore.KeyValueBase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeDataStoreDefault @Inject constructor(
    @ApplicationContext private val context: Context
) : ThemeDataStore {

    private val baseKeyValue = KeyValueBase(context)

    override fun readTheme(): Flow<Boolean?> {
        return baseKeyValue.readBoolean(THEME_KEY)
    }

    override suspend fun writeTheme(value: Boolean) {
        baseKeyValue.writeBoolean(THEME_KEY, value)
    }

    companion object {
        private const val THEME_KEY = "theme"
    }
}