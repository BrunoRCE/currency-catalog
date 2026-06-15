package com.github.brunorce.currencycatalog.util

import com.github.brunorce.currencycatalog.domain.Currency
import java.util.Locale

object SystemCurrencyProvider {
    fun get(): Currency {
        return try {
            val locale = Locale.getDefault()
            val javaCurrency = java.util.Currency.getInstance(locale)

            Currency(
                id = 0,
                code = javaCurrency.currencyCode,
                name = javaCurrency.getDisplayName(locale),
                symbol = javaCurrency.getSymbol(locale)
            )
        } catch (e: Exception) {
            Currency(0, "USD", "United States Dollar", "$")
        }
    }
}