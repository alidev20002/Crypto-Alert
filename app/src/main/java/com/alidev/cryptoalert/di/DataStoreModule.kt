package com.alidev.cryptoalert.di

import com.alidev.cryptoalert.data.datastore.ConditionDataStoreDefault
import com.alidev.cryptoalert.data.datastore.ConditionDataStore
import com.alidev.cryptoalert.data.datastore.DstCurrencyDataStore
import com.alidev.cryptoalert.data.datastore.DstCurrencyDataStoreDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    @Singleton
    fun bindConditionDataStore(
        dataStore: ConditionDataStoreDefault
    ): ConditionDataStore

    @Binds
    @Singleton
    fun bindDstCurrencyDataStore(
        dataStoreDefault: DstCurrencyDataStoreDefault
    ): DstCurrencyDataStore
}