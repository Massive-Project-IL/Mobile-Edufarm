package com.example.edufarm.data.model

data class RegisterRequest(
    val email_user: String,
    val password: String,
    val nama_user: String,
    val telpon_user: String
)

data class RegisterResponse(
    val msg: String,
    val token: String
)