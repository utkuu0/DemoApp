package com.utkuaksu.demoapp.data.remote.share

import com.utkuaksu.demoapp.data.model.share.ShareResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface ShareApi {

    @GET("economy/hisseSenedi")
    suspend fun getShares(
        @Header("Authorization") apiKey: String
    ): Response<ShareResponse>  // Response ile döndür

    companion object {
        fun create(): ShareApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.collectapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ShareApi::class.java)
        }
    }
}