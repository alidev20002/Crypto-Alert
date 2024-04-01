package com.alidev.cryptoalert.data.datastore

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DstCurrencyDataStoreDefault @Inject constructor(
    @ApplicationContext private val context: Context
) : DstCurrencyDataStore {

    private val baseKeyValue = KeyValueBase(context)

    override fun readDstCurrency(): Flow<String?> {
        return baseKeyValue.readString(CONDITIONS_KEY)
    }

    override suspend fun writeDstCurrency(value: String) {
        baseKeyValue.writeString(CONDITIONS_KEY, value)
    }

    override fun readDstCurrencySync(): String? {
        return baseKeyValue.readStringSync(CONDITIONS_KEY)
    }

    override fun writeDstCurrencySync(value: String) {
        baseKeyValue.writeStringSync(CONDITIONS_KEY, value)
    }

    companion object {
        private const val CONDITIONS_KEY = "dstCurrency"
    }
}