package com.utkuaksu.demoapp.data.model.emtia

data class Emtia (
    val name: String,
    val test: String,
    val buying: Double,
    val selling: Double,
    val rate: Double
)

data class EmtiaResponse(
    val success: Boolean,
    val result: List<Emtia>
)