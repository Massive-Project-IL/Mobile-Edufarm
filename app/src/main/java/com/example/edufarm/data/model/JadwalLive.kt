package com.example.edufarm.data.model

// ApiResponse model for wrapping API responses
data class ApiLiveResponse(
    val msg: String, // Pesan dari API
    val data: List<JadwalLive> // Data yang berisi daftar jadwal live
)

// Data model untuk setiap item jadwal live
data class JadwalLive(
    val notifikasi_id: Int,        // ID notifikasi
    val judul_notifikasi: String, // Judul notifikasi
    val tanggal: String,          // Tanggal jadwal
    val deskripsi: String,        // Deskripsi jadwal
    val poin: String,             // Poin informasi
    val kategori_id: Int,         // ID kategori
    val nama_kategori: String,    // Nama kategori
    val nama_mentor: String,      // Nama mentor
    val link_zoom: String,        // Link Zoom untuk jadwal
    val waktu_mulai: String,      // Waktu mulai jadwal
    val waktu_selesai: String,    // Waktu selesai jadwal
    val durasi: String            // Durasi jadwal
)

