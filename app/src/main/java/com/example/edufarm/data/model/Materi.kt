package com.example.edufarm.data.model


data class ApiMateriResponse(
    val success: Boolean,
    val data: List<Materi>
)

data class Materi(
    val modul_id: Int,
    val nama_modul: String,
    val text_module: String?,
    val gambar: String?,
    val video: String?,
    val file: String?,
    val tanggal_modul: String,
    val kategori_id: Int
)