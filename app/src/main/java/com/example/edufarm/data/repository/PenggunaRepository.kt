package com.example.edufarm.data.repository

import com.example.edufarm.data.api.ApiService
import com.example.edufarm.data.model.Pengguna

class PenggunaRepository(private val apiService: ApiService) {

    // Fungsi untuk mengambil data pengguna
    suspend fun getPengguna(authorization: String): Pengguna {
        val response = apiService.getPengguna(authorization)
        if (response.isSuccessful) {
            val profileResponse = response.body()
            val penggunaList = profileResponse?.data.orEmpty() // Default ke list kosong
            if (penggunaList.isNotEmpty()) {
                val pengguna = penggunaList.first()
                return pengguna.copy(
                    foto_profile = pengguna.foto_profile?.takeIf { it != "null" }
                )
            } else {
                throw Exception("Data pengguna kosong, periksa API atau data di server")
            }
        } else {
            throw Exception("Gagal memuat data pengguna: ${response.message()} (code: ${response.code()})")
        }
    }

    // Fungsi untuk mengedit data pengguna
    suspend fun editPengguna(authorization: String, updatedProfile: Pengguna): Pengguna {
        val response = apiService.editProfile(authorization, updatedProfile)
        if (response.isSuccessful) {
            val profileResponse = response.body()
            val penggunaList = profileResponse?.data.orEmpty()
            if (penggunaList.isNotEmpty()) {
                return penggunaList.first()
            } else {
                throw Exception("Data pengguna kosong setelah update")
            }
        } else {
            throw Exception("Gagal mengupdate data pengguna: ${response.message()} (code: ${response.code()})")
        }
    }
}

