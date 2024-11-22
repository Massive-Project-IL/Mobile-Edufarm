package com.example.edufarm.data.api

import com.example.edufarm.data.model.Materi
import retrofit2.http.GET

interface MateriApiService {
    @GET("materi")
    suspend fun getMateriList(): List<Materi>
}