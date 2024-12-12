package com.example.edufarm.data.model

data class PasswordUpdateRequest(
    val email_user: String,
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
)

data class PasswordResponse(
    val msg: String
)