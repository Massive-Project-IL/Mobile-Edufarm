package com.example.edufarm.data.api

import com.example.edufarm.data.model.ApiKategoriResponse
import com.example.edufarm.data.model.ApiLiveResponse
import com.example.edufarm.data.model.ApiMateriResponse
import com.example.edufarm.data.model.LoginRequest
import com.example.edufarm.data.model.LoginResponse
import com.example.edufarm.data.model.PasswordResponse
import com.example.edufarm.data.model.PasswordUpdateRequest
import com.example.edufarm.data.model.Pengguna
import com.example.edufarm.data.model.ProfileResponse
import com.example.edufarm.data.model.RegisterRequest
import com.example.edufarm.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    // Login endpoint
    @POST("api/v1/mobilepengguna/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    // Daftar endpoint
    @POST("api/v1/mobilepengguna/daftar")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    // Pengguna endpoint
    @GET("api/v1/mobilepengguna")
    suspend fun getPengguna(
        @Header("Authorization") authorization: String
    ): Response<ProfileResponse>

    // Update Pengguna endpoint
    @PUT("api/v1/mobilepengguna/profile")
    suspend fun editProfile(
        @Header("Authorization") authorization: String,
        @Body updatedProfile: Pengguna
    ): Response<ProfileResponse>

    // Update Pengguna Password endpoint
    @PUT("api/v1/mobilepengguna/password")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Body passwordUpdateRequest: PasswordUpdateRequest
    ): PasswordResponse


    // Kategori
    @GET("api/v1/mobilekategori")
    suspend fun getKategori(): Response<ApiKategoriResponse>

    // Modul berdasarkan kategori_id
    @GET("api/v1/mobilemodule")
    suspend fun getMateriByCategory(
        @Query("kategori_id") kategoriId: Int
    ): Response<ApiMateriResponse>

    // Jadwal Live
    @GET("api/v1/mobilenotifikasi")
    suspend fun getJadwalLive(): Response<ApiLiveResponse>
}
