package com.utkuaksu.demoapp.data.repository.currency

import android.content.Context
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.currency.CurrencyResponse
import com.utkuaksu.demoapp.data.remote.currency.CurrencyApi
import okhttp3.ResponseBody
import retrofit2.Response

class CurrencyRepository(private val api: CurrencyApi, private val context: Context) {

    suspend fun getCurrencies(): Response<CurrencyResponse> {
        return try {
            val apiKey = context.getString(R.string.collect_api_key)
            Response.success(api.getCurrencies(apiKey))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, ResponseBody.create(null, "Network Error"))
        }
    }
}