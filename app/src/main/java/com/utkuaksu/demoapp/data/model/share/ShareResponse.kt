package com.utkuaksu.demoapp.data.model.share

data class Share(
    val code: String,
    val text: String,
    val lastprice: Double,
    val rate: Double,
    val icon: String
)

data class ShareResponse(
    val success: Boolean,
    val result: List<Share>
)
