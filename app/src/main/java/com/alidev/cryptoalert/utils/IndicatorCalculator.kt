package com.alidev.cryptoalert.utils

import kotlin.math.abs
import kotlin.math.sqrt

object IndicatorCalculator {

    fun sma(prices: List<Double>, period: Int): Double {
        val smaValues = MutableList(prices.size) { 0.0 }
        for (i in period until prices.size) {
            smaValues[i] = prices.subList(i - period, i).average()
        }
        return smaValues.last()
    }

    fun ema(prices: List<Double>, period: Int): Double {
        val weightingFactor = 2.0 / (period + 1)
        val emaValues = MutableList(prices.size) { 0.0 }
        for (i in period until prices.size) {
            emaValues[i] = (prices[i] * weightingFactor) + (emaValues[i - 1] * (1 - weightingFactor))
        }
        return emaValues.last()
    }

    private fun emaList(prices: List<Double>, period: Int): List<Double> {
        val weightingFactor = 2.0 / (period + 1)
        val emaValues = MutableList(prices.size) { 0.0 }
        for (i in period until prices.size) {
            emaValues[i] = (prices[i] * weightingFactor) + (emaValues[i - 1] * (1 - weightingFactor))
        }
        return emaValues
    }

    fun rsi(prices: List<Double>, period: Int): Double {
        val lastPriceIndex = prices.lastIndex
        val firstPriceIndex = lastPriceIndex - period + 1
        var aveGain = 0.0
        var aveLoss = 0.0
        for (priceIndex in firstPriceIndex..lastPriceIndex) {
            val change = prices[priceIndex] - prices[priceIndex - 1]
            if (change >= 0) {
                aveGain += change.toInt()
            } else {
                aveLoss += change.toInt()
            }
        }

        val rs = aveGain / abs(aveLoss)
        return 100 - 100.0 / (1.0 + rs)
    }

    fun macd(prices: List<Double>, shortPeriod: Int, longPeriod: Int, signalPeriod: Int): Double {
        val emaShort = emaList(prices, shortPeriod)
        val emaLong = emaList(prices, longPeriod)
        val macdResult = mutableListOf<Double>()
        emaShort.forEachIndexed() { index, item ->
            macdResult.add(item - emaLong[index])
        }
        val signal = emaList(macdResult, signalPeriod)
        return macdResult.last() - signal.last()
    }

    fun wma(prices: List<Double>, period: Int): Double {
        val results = DoubleArray(prices.size - period + 1)
        var wma: Double
        for ((j, i) in (period - 1 until prices.size).withIndex()) {
            val pricesAux = prices.subList(i - period + 1, i + 1)
            wma = calculateWMA(pricesAux)
            results[j] = wma
        }

        return results.toList().last()
    }

    private fun wmaList(prices: List<Double>, period: Int): List<Double> {
        val results = DoubleArray(prices.size - period + 1)
        var wma: Double
        for ((j, i) in (period - 1 until prices.size).withIndex()) {
            val pricesAux = prices.subList(i - period + 1, i + 1)
            wma = calculateWMA(pricesAux)
            results[j] = wma
        }

        return results.toList()
    }

    private fun calculateWMA(prices: List<Double>): Double {
        var wma = 0.0
        var total = 0

        for (i in prices.indices) {
            wma += prices[i] * (i + 1)
            total += i + 1
        }
        wma /= total.toDouble()

        return wma
    }

    fun hma(prices: List<Double>, period: Int): Double {
        val wmas = wmaList(prices, period/2).map { it * 2 }
        val wmal = wmaList(prices, period)
        val diff = wmas.size - wmal.size
        val rawHma = mutableListOf<Double>()
        wmal.forEachIndexed { index, item ->
            val res = wmas[index + diff] - item
            rawHma.add(res)
        }
        val newPeriod = sqrt(period.toDouble())
        val hmaList = wmaList(rawHma, newPeriod.toInt())
        return hmaList.last()
    }
}