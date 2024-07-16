package com.alidev.cryptoalert.data.datasource.condition

import com.alidev.cryptoalert.data.datastore.condition.ConditionDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConditionDataSourceDefault @Inject constructor(
    private val conditionDataStore: ConditionDataStore
) : ConditionDataSource {

    override fun readConditions(): Flow<String?> {
        return conditionDataStore.readConditions()
    }

    override suspend fun writeConditions(value: String) {
        conditionDataStore.writeConditions(value)
    }

    override fun readConditionsSync(): String? {
        return conditionDataStore.readConditionsSync()
    }

    override fun writeConditionsSync(value: String) {
        conditionDataStore.writeConditionsSync(value)
    }
}