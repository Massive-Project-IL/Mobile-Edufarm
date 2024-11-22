package com.example.edufarm.data.repository

import com.example.edufarm.data.api.AuthApiService
import com.example.edufarm.data.model.LoginRequest
import com.example.edufarm.data.model.LoginResponse

class AuthRepository(private val authApiService: AuthApiService) {
    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        return authApiService.login(request)
    }
}