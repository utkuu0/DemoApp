package com.utkuaksu.demoapp.api

import com.google.gson.GsonBuilder
import com.utkuaksu.demoapp.model.CurrencyResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CurrencyApi {

    @GET("economy/allCurrency")
    suspend fun getCurrencies(): CurrencyResponse

    companion object {
        fun create(apiKey: String): CurrencyApi {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        // CollectAPI için doğru header
                        .addHeader("Authorization", "apikey $apiKey")
                        .build()
                    chain.proceed(request)
                }
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl("https://api.collectapi.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(CurrencyApi::class.java)
        }
    }
}
