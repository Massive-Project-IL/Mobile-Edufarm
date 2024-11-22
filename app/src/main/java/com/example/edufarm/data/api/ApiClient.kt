package com.example.edufarm.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8000/" // Gunakan ini jika menggunakan emulator
    // private const val BASE_URL = "http://192.168.1.100:8000/" // Gunakan ini jika perangkat fisik
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}