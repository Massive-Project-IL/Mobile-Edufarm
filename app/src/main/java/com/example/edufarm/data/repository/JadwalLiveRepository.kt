package com.example.edufarm.data.repository

import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.model.JadwalLive

class JadwalLiveRepository {
    suspend fun getJadwalLive(): List<JadwalLive>? {
        val response = ApiClient.apiService.getJadwalLive()
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }
}
