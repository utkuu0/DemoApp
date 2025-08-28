package com.utkuaksu.demoapp.data.repository.share

import android.content.Context
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.share.ShareResponse
import com.utkuaksu.demoapp.data.remote.share.ShareApi
import okhttp3.ResponseBody
import retrofit2.Response

class ShareRepository(private val api: ShareApi, private val context: Context) {

    suspend fun getShares(): Response<ShareResponse> {
        return try {
            return api.getShares(context.getString(R.string.collect_api_key))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, ResponseBody.create(null, "Network Error"))
        }
    }
}