package com.utkuaksu.demoapp.data.remote.currency

import com.utkuaksu.demoapp.data.model.currency.CurrencyResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface CurrencyApi {

    @GET("economy/allCurrency")
    suspend fun getCurrencies(
        @Header("Authorization") apiKey: String
    ): CurrencyResponse

    companion object {
        fun create(): CurrencyApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.collectapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CurrencyApi::class.java)
        }
    }
}