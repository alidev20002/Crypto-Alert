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

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindCryptoMarketDataSource(
        dataSource: CryptoMarketDataSourceDefault
    ): CryptoMarketDataSource

    @Binds
    fun bindCryptoConditionDataSource(
        dataSource: ConditionDataSourceDefault
    ): ConditionDataSource

    @Binds
    fun bindIndicatorDataSource(
        dataSource: IndicatorDataSourceDefault
    ): IndicatorDataSource

    @Binds
    fun bindCryptoCandlesDataSource(
        dataSource: CryptoCandlesDataSourceDefault
    ): CryptoCandlesDataSource
}