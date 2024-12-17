package com.example.edufarm.data.model

// Request model untuk POST API
data class BookmarkRequest(
    val email_user: String,
    val kategori_id: Int
)

// Response model untuk toggle
data class ToggleResponse(
    val success: Boolean,
    val message: String,
    val isBookmarked: Boolean
)

// Response model untuk generic response
data class GenericResponse(
    val success: Boolean,
    val message: String
)

// Model untuk respons API
data class ApiResponse(
    val success: Boolean,
    val data: List<Bookmark>
)


// Model untuk bookmark yang diambil (GET)
data class Bookmark(
    val id: Int,
    val email_user: String,
    val kategori_id: Int,
    val nama_kategori: String,
    val penjelasan: String,
    val gambar: String
)
