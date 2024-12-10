package com.example.edufarm.data.repository

import com.example.edufarm.data.api.ApiService
import com.example.edufarm.data.model.LoginRequest
import com.example.edufarm.data.model.LoginResponse
import com.example.edufarm.data.model.RegisterRequest
import com.example.edufarm.data.model.RegisterResponse
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return apiService.login(LoginRequest(email, password))
    }


    // Fungsi untuk daftar pengguna
    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return apiService.registerUser(registerRequest)
    }

}