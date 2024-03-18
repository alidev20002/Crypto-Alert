package com.alidev.cryptoalert.di

import com.alidev.cryptoalert.data.api.CryptoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCryptoApi(): CryptoApi {
        return Retrofit.Builder()
            .baseUrl("https://api.nobitex.ir/market/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }
}