package com.example.edufarm.data.model

// Model data untuk jadwal
data class Jadwal(
    val id: Int, // ID unik
    val title: String,
    val timeRange: String,
    val mentor: String
)