package com.example.edufarm.data.model

data class LoginRequest(
    val email_user: String,
    val password: String
)

data class LoginResponse(
    val msg: String,
    val token: String
)
