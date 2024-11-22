package com.example.edufarm.data.api

import com.example.edufarm.data.model.LoginRequest
import com.example.edufarm.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login") // Ganti dengan endpoint login API Anda
    suspend fun login(@Body request: LoginRequest): LoginResponse
}