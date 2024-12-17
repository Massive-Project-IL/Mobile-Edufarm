package com.example.edufarm.data.model

import com.google.gson.annotations.SerializedName


data class IdTokenRequest(
    val idToken: String
)

data class GoogleLoginResponse(
    @SerializedName("msg") val message: String,
    @SerializedName("user") val user: GoogleUser,
    @SerializedName("token") val token: String
)

data class GoogleUser(
    @SerializedName("email_user") val email: String,
    @SerializedName("nama_user") val name: String,
    @SerializedName("foto_profile") val profilePicture: String?
)


