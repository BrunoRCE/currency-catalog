package com.github.brunorce.currencycatalog.provider

import com.github.brunorce.currencycatalog.data.CurrencyCatalog
import com.github.brunorce.currencycatalog.domain.Currency
import com.github.brunorce.currencycatalog.util.SystemCurrencyProvider

object CurrencyProvider {
    fun getAll(): List<Currency> {
        val systemCurrency = SystemCurrencyProvider.get()

        val filtered = CurrencyCatalog.currencies
            .filterNot { it.code == systemCurrency.code }

        return listOf(systemCurrency) + filtered
    }

    fun findByCode(code: String): Currency? {
        return getAll().find { it.code == code }
    }
}