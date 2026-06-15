package com.github.brunorce.currencycatalog.domain

data class Currency(
    val id: Int,
    val code: String,
    val name: String,
    val symbol: String
)