package com.utkuaksu.demoapp.model

data class CurrencyResponse(
    val success: Boolean,
    val result: List<Currency>
)

data class Currency(
    val code: String,
    val name: String,
    val buying: Double,
    val selling: Double,
    val rate: Double
)


