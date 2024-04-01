package com.alidev.cryptoalert.data.datasource.dstcurrency

import com.alidev.cryptoalert.data.datastore.DstCurrencyDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DstCurrencyDataSourceDefault @Inject constructor(
    private val dstCurrencyDataStore: DstCurrencyDataStore
) : DstCurrencyDataSource {

    override fun readDstCurrency(): Flow<String?> {
        return dstCurrencyDataStore.readDstCurrency()
    }

    override suspend fun writeDstCurrency(value: String) {
        dstCurrencyDataStore.writeDstCurrency(value)
    }

    override fun readDstCurrencySync(): String? {
        return dstCurrencyDataStore.readDstCurrencySync()
    }

    override fun writeDstCurrencySync(value: String) {
        dstCurrencyDataStore.writeDstCurrencySync(value)
    }
}