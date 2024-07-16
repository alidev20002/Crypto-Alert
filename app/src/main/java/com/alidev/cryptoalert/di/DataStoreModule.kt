package com.alidev.cryptoalert.di

import com.alidev.cryptoalert.data.datastore.condition.ConditionDataStoreDefault
import com.alidev.cryptoalert.data.datastore.condition.ConditionDataStore
import com.alidev.cryptoalert.data.datastore.dstcurrency.DstCurrencyDataStore
import com.alidev.cryptoalert.data.datastore.dstcurrency.DstCurrencyDataStoreDefault
import com.alidev.cryptoalert.data.datastore.theme.ThemeDataStore
import com.alidev.cryptoalert.data.datastore.theme.ThemeDataStoreDefault
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
        dataStore: DstCurrencyDataStoreDefault
    ): DstCurrencyDataStore

    @Binds
    @Singleton
    fun bindThemeDataStore(
        dataStore: ThemeDataStoreDefault
    ): ThemeDataStore
}