package com.utkuaksu.demoapp.data.remote.emtia

import com.utkuaksu.demoapp.data.model.emtia.EmtiaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface EmtiaApi {

    @GET("economy/emtia")
    suspend fun getEmtias(
        @Header("Authorization") apiKey: String
    ): EmtiaResponse

    companion object {
        fun create(): EmtiaApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.collectapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(EmtiaApi::class.java)
        }
    }
}