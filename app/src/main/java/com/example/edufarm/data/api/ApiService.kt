package com.example.edufarm.data.api

import com.example.edufarm.data.model.ApiKategoriResponse
import com.example.edufarm.data.model.ApiLiveResponse
import com.example.edufarm.data.model.ApiMateriResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // Kategori
    @GET("api/v1/mobilekategori")
    suspend fun getKategori(): Response<ApiKategoriResponse>

    // Modul berdasarkan kategori_id
    @GET("api/v1/mobilemodule")
    suspend fun getMateriByCategory(
        @Query("kategori_id") kategoriId: Int
    ): Response<ApiMateriResponse>

    // Jadwal Live
    @GET("api/v1/mobilenotifikasi") // Endpoint API Anda
    suspend fun getJadwalLive(): Response<ApiLiveResponse>
}
