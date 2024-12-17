package com.example.edufarm.data.model


data class ApiLiveResponse(
    val msg: String,
    val data: List<JadwalLive>
)

data class JadwalLive(
    val notifikasi_id: Int,
    val judul_notifikasi: String,
    val tanggal: String,
    val deskripsi: String,
    val poin: String,
    val kategori_id: Int,
    val nama_kategori: String,
    val nama_mentor: String,
    val link_zoom: String,
    val waktu_mulai: String,
    val waktu_selesai: String,
    val durasi: String
)

