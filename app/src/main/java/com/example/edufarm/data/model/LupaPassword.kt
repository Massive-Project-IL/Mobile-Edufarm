package com.example.edufarm.data.model

import com.google.gson.annotations.SerializedName

// Request Body untuk Lupa Password
data class LupaPasswordRequest(
    val email_user: String
)

// Request Body untuk Reset Password
data class ResetPasswordRequest(
    val email_user: String,
    val otp: String,
    val newPassword: String,
    val confirmPassword: String
)

// Response Message
data class GeneralResponse(
    val msg: String
)

data class VerifikasiOtpRequest(
    @SerializedName("email_user") val email_user: String,
    @SerializedName("otp") val otp: String
)

data class GeneralResponseVerifikasi(
    @SerializedName("msg") val msg: String
)
