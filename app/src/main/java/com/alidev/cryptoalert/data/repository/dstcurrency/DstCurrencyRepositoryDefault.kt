package com.alidev.cryptoalert.data.repository.dstcurrency

import com.alidev.cryptoalert.data.datasource.dstcurrency.DstCurrencyDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DstCurrencyRepositoryDefault @Inject constructor(
    private val dstCurrencyDataSource: DstCurrencyDataSource
) : DstCurrencyRepository {

    override fun readDstCurrency(): Flow<String> {
        return dstCurrencyDataSource.readDstCurrency().map {
            it ?: "rls"
        }
    }

    override suspend fun writeDstCurrency(value: String) {
        dstCurrencyDataSource.writeDstCurrency(value)
    }

    override fun readDstCurrencySync(): String {
        return dstCurrencyDataSource.readDstCurrencySync().let {
            it ?: "rls"
        }
    }

    override fun writeDstCurrencySync(value: String) {
        dstCurrencyDataSource.writeDstCurrencySync(value)
    }
}