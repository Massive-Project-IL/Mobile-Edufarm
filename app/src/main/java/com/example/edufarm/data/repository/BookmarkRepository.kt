package com.example.edufarm.data.repository

import com.example.edufarm.data.api.ApiService
import com.example.edufarm.data.model.ApiResponse
import com.example.edufarm.data.model.BookmarkRequest
import com.example.edufarm.data.model.GenericResponse
import com.example.edufarm.data.model.ToggleResponse
import retrofit2.Response

class BookmarkRepository(private val apiService: ApiService) {

    suspend fun addBookmark(request: BookmarkRequest): Response<GenericResponse> {
        return apiService.addBookmark(request)
    }

    suspend fun toggleBookmark(request: BookmarkRequest): Response<ToggleResponse> {
        return apiService.toggleBookmark(request)
    }

    suspend fun getBookmarks(emailUser: String): Response<ApiResponse> {
        return apiService.getBookmarks(emailUser)
    }

    suspend fun deleteAllBookmarks(emailUser: String): Response<GenericResponse> {
        return apiService.deleteAllBookmarks(emailUser)
    }

    suspend fun deleteSpecificBookmark(request: BookmarkRequest): Response<GenericResponse> {
        return apiService.deleteSpecificBookmark(request)
    }
}

