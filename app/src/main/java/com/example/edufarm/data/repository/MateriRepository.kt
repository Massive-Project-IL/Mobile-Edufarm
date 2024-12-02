package com.example.edufarm.data.repository

import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.model.Materi

class MateriRepository {
    suspend fun getMateriByCategory(kategoriId: Int): List<Materi>? {
        val response = ApiClient.apiService.getMateriByCategory(kategoriId)
        return if (response.isSuccessful && response.body()?.success == true) {
            response.body()?.data
        } else {
            null
        }
    }
}