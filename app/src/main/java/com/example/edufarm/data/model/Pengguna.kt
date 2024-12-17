package com.example.edufarm.data.model

data class ProfileResponse(
    val msg: String,
    val data: Pengguna
)

data class Pengguna(
    val nama_user: String?,
    val email_user: String?,
    val telpon_user: String?,
    val foto_profile: String?,
    val is_default_password: Boolean // Tambahkan properti ini
)

