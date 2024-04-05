package com.alidev.cryptoalert.data.repository.condition

import com.alidev.cryptoalert.data.datasource.condition.ConditionDataSource
import com.alidev.cryptoalert.ui.model.CryptoCondition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ConditionRepositoryDefault @Inject constructor(
    private val conditionDataSource: ConditionDataSource
) : ConditionRepository {

    override fun readConditions(): Flow<List<CryptoCondition>> {
        return conditionDataSource.readConditions().map {
            if (it == null)
                emptyList()
            else
                Json.decodeFromString(it)
        }
    }

    override suspend fun writeConditions(conditions: List<CryptoCondition>) {
        val conditionsAsJson = Json.encodeToString(conditions)
        conditionDataSource.writeConditions(conditionsAsJson)
    }

    override fun readConditionsSync(): List<CryptoCondition> {
        return conditionDataSource.readConditionsSync().let {
            if (it == null)
                emptyList()
            else
                Json.decodeFromString(it)
        }
    }

    override fun writeConditionsSync(conditions: List<CryptoCondition>) {
        val conditionsAsJson = Json.encodeToString(conditions)
        conditionDataSource.writeConditionsSync(conditionsAsJson)
    }
}