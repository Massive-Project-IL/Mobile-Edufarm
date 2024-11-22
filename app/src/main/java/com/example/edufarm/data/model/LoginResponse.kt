package com.example.edufarm.data.model


data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String
)