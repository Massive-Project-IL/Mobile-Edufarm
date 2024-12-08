package com.example.edufarm.data.model


data class ApiKategoriResponse(
    val success: Boolean,
    val data: List<Kategori>
)

data class Kategori(
    val kategori_id: Int,
    val nama_kategori: String,
    val penjelasan: String,
    val gambar: String,
    val icon: String?,
    val tanggal_dibuat: String,
    val email_mentor:String?
)