package com.example.edufarm.data.repository

import android.util.Log
import com.example.edufarm.data.api.ApiService
import com.example.edufarm.data.model.GeneralResponse
import com.example.edufarm.data.model.GeneralResponseVerifikasi
import com.example.edufarm.data.model.GoogleLoginResponse
import com.example.edufarm.data.model.IdTokenRequest
import com.example.edufarm.data.model.LoginRequest
import com.example.edufarm.data.model.LoginResponse
import com.example.edufarm.data.model.LupaPasswordRequest
import com.example.edufarm.data.model.RegisterRequest
import com.example.edufarm.data.model.RegisterResponse
import com.example.edufarm.data.model.ResetPasswordRequest
import com.example.edufarm.data.model.VerifikasiOtpRequest
import com.google.gson.Gson
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    // Fungsi login biasa
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return apiService.login(LoginRequest(email, password))
    }

    // Fungsi untuk daftar pengguna
    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return apiService.registerUser(registerRequest)
    }

    // Fungsi login dengan Google
    suspend fun loginWithGoogle(requestBody: IdTokenRequest): Response<GoogleLoginResponse> {
        return apiService.loginWithGoogle(requestBody)
    }

    // Fungsi untuk lupa password
    suspend fun requestOtp(email: String): Response<GeneralResponse> {
        val request = LupaPasswordRequest(email_user = email)
        return apiService.lupaPassword(request)
    }

    suspend fun resetPassword(
        email: String,
        otp: String,
        passwordBaru: String,
        konfirmasiPassword: String
    ): Response<GeneralResponse> {
        val request = ResetPasswordRequest(
            email_user = email,
            otp = otp,
            newPassword = passwordBaru,
            confirmPassword = konfirmasiPassword
        )
        Log.d("Repository", "Request Body: ${Gson().toJson(request)}")
        return apiService.resetPassword(request)
    }

    // Fungsi Verifikasi OTP
    suspend fun verifikasiOtp(email: String, otp: String): Response<GeneralResponseVerifikasi> {
        val request = VerifikasiOtpRequest(email_user = email, otp = otp)
        return apiService.verifikasiOtp(request)
    }

}