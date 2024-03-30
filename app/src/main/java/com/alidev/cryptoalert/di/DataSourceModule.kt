package com.alidev.cryptoalert.di

import com.alidev.cryptoalert.data.datasource.candle.CryptoCandlesDataSource
import com.alidev.cryptoalert.data.datasource.candle.CryptoCandlesDataSourceDefault
import com.alidev.cryptoalert.data.datasource.candle.IndicatorDataSource
import com.alidev.cryptoalert.data.datasource.candle.IndicatorDataSourceDefault
import com.alidev.cryptoalert.data.datasource.condition.ConditionDataSource
import com.alidev.cryptoalert.data.datasource.condition.ConditionDataSourceDefault
import com.alidev.cryptoalert.data.datasource.stats.CryptoMarketDataSource
import com.alidev.cryptoalert.data.datasource.stats.CryptoMarketDataSourceDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindCryptoMarketDataSource(
        dataSource: CryptoMarketDataSourceDefault
    ): CryptoMarketDataSource

    @Binds
    @Singleton
    fun bindCryptoConditionDataSource(
        dataSource: ConditionDataSourceDefault
    ): ConditionDataSource

    @Binds
    @Singleton
    fun bindIndicatorDataSource(
        dataSource: IndicatorDataSourceDefault
    ): IndicatorDataSource

    @Binds
    @Singleton
    fun bindCryptoCandlesDataSource(
        dataSource: CryptoCandlesDataSourceDefault
    ): CryptoCandlesDataSource
}