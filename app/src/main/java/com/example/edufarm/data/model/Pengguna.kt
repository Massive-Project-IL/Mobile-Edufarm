package com.example.edufarm.data.model

data class ProfileResponse(
    val msg: String,
    val data: List<Pengguna>
)

data class Pengguna(
    val nama_user: String?,
    val email_user: String?,
    val telpon_user: String?,
    val foto_profile: String?
)
