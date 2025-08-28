package com.utkuaksu.demoapp.data.repository.emtia

import android.content.Context
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.emtia.EmtiaResponse
import com.utkuaksu.demoapp.data.remote.emtia.EmtiaApi
import okhttp3.ResponseBody
import retrofit2.Response

class EmtiaRepository(private val api: EmtiaApi, private val context: Context) {

    suspend fun getEmtias(): Response<EmtiaResponse> {
        return try {
            val apiKey = context.getString(R.string.collect_api_key)
            Response.success(api.getEmtias(apiKey))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, ResponseBody.create(null, "NetworkError"))
        }
    }
}