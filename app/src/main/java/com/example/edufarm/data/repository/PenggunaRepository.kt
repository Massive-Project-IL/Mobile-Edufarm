package com.example.edufarm.data.repository

import android.util.Log
import com.example.edufarm.data.api.ApiService
import com.example.edufarm.data.model.PasswordResponse
import com.example.edufarm.data.model.PasswordUpdateRequest
import com.example.edufarm.data.model.Pengguna

class PenggunaRepository(private val apiService: ApiService) {

    // Fungsi untuk mengambil data pengguna
    suspend fun getPengguna(authorization: String): Pengguna {
        val response = apiService.getPengguna(authorization)
        if (response.isSuccessful) {
            val profileResponse = response.body()
            val pengguna = profileResponse?.data
            if (pengguna != null) {
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
    suspend fun editPengguna(authorization: String, updatedProfile: Pengguna): String {
        val response = apiService.editProfile(authorization, updatedProfile)
        if (response.isSuccessful) {
            val profileResponse = response.body()
            return profileResponse?.msg ?: "Profil berhasil diperbarui."
        } else {
            throw Exception("Gagal mengupdate data pengguna: ${response.message()} (code: ${response.code()})")
        }
    }

    suspend fun updatePassword(token: String, passwordUpdateRequest: PasswordUpdateRequest): PasswordResponse {
        Log.d("UpdatePassword", "Header Authorization: $token")
        Log.d("UpdatePassword", "Body Request: $passwordUpdateRequest")
        return apiService.updatePassword(token, passwordUpdateRequest)
    }

}





