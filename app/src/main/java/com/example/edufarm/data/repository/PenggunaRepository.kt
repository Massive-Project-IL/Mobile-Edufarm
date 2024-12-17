package com.example.edufarm.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.edufarm.data.api.ApiService
import com.example.edufarm.data.model.PasswordResponse
import com.example.edufarm.data.model.PasswordUpdateRequest
import com.example.edufarm.data.model.Pengguna
import com.example.edufarm.data.model.ProfileResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

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


    suspend fun updatePassword(token: String, passwordUpdateRequest: PasswordUpdateRequest): PasswordResponse {
        Log.d("UpdatePassword", "Header Authorization: $token")
        Log.d("UpdatePassword", "Body Request: $passwordUpdateRequest")
        return apiService.updatePassword(token, passwordUpdateRequest)
    }

    suspend fun updateProfile(
        token: String,
        namaUser: String,
        emailUser: String,
        telponUser: String,
        fotoUri: Uri?,
        context: Context
    ): Response<ProfileResponse> {
        val namaRequestBody = namaUser.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailRequestBody = emailUser.toRequestBody("text/plain".toMediaTypeOrNull())
        val telponRequestBody = telponUser.toRequestBody("text/plain".toMediaTypeOrNull())

        val fotoPart = fotoUri?.let {
            val file = File(it.path!!)
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("foto_profile", file.name, requestFile)
        }

        return apiService.updateProfile(
            authorization = "Bearer $token",
            namaUser = namaRequestBody,
            emailUser = emailRequestBody,
            telponUser = telponRequestBody,
            foto_profile = fotoPart
        )
    }
}





