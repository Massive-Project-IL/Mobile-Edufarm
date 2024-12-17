package com.example.edufarm.data.api

import com.example.edufarm.data.model.ApiKategoriResponse
import com.example.edufarm.data.model.ApiLiveResponse
import com.example.edufarm.data.model.ApiMateriResponse
import com.example.edufarm.data.model.ApiResponse
import com.example.edufarm.data.model.BookmarkRequest
import com.example.edufarm.data.model.GeneralResponse
import com.example.edufarm.data.model.GeneralResponseVerifikasi
import com.example.edufarm.data.model.GenericResponse
import com.example.edufarm.data.model.GoogleLoginResponse
import com.example.edufarm.data.model.IdTokenRequest
import com.example.edufarm.data.model.LoginRequest
import com.example.edufarm.data.model.LoginResponse
import com.example.edufarm.data.model.LupaPasswordRequest
import com.example.edufarm.data.model.PasswordResponse
import com.example.edufarm.data.model.PasswordUpdateRequest
import com.example.edufarm.data.model.ProfileResponse
import com.example.edufarm.data.model.RegisterRequest
import com.example.edufarm.data.model.RegisterResponse
import com.example.edufarm.data.model.ResetPasswordRequest
import com.example.edufarm.data.model.ToggleResponse
import com.example.edufarm.data.model.VerifikasiOtpRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Login endpoint
    @POST("api/v1/mobilepengguna/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    // Login dengan Google endpoint
    @POST("api/v1/mobilepengguna/auth/google/callback")
    suspend fun loginWithGoogle(@Body body: IdTokenRequest): Response<GoogleLoginResponse>

    // Daftar endpoint
    @POST("api/v1/mobilepengguna/daftar")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    // Lupa password endpoint
    @POST("api/v1/mobilepengguna/lupapassword")
    suspend fun lupaPassword(@Body request: LupaPasswordRequest): Response<GeneralResponse>

    @POST("api/v1/mobilepengguna/verifikasiotp")
    suspend fun verifikasiOtp(@Body request: VerifikasiOtpRequest): Response<GeneralResponseVerifikasi>

    // Reset password endpoint
    @POST("api/v1/mobilepengguna/resetpassword")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<GeneralResponse>

    // Pengguna endpoint
    @GET("api/v1/mobilepengguna")
    suspend fun getPengguna(
        @Header("Authorization") authorization: String
    ): Response<ProfileResponse>

    // Update Pengguna endpoint
    @Multipart
    @PUT("api/v1/mobilepengguna/profile")
    suspend fun updateProfile(
        @Header("Authorization") authorization: String,
        @Part("nama_user") namaUser: RequestBody,
        @Part("email_user") emailUser: RequestBody,
        @Part("telpon_user") telponUser: RequestBody,
        @Part foto_profile: MultipartBody.Part? // Gambar opsional
    ): Response<ProfileResponse>


    // Update Pengguna Password endpoint
    @PUT("api/v1/mobilepengguna/password")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Body passwordUpdateRequest: PasswordUpdateRequest
    ): PasswordResponse

    // Kategori
    @GET("api/v1/mobilekategori")
    suspend fun getKategori(): Response<ApiKategoriResponse>

    // Modul berdasarkan kategori_id
    @GET("api/v1/mobilemodule")
    suspend fun getMateriByCategory(
        @Query("kategori_id") kategoriId: Int
    ): Response<ApiMateriResponse>

    // Jadwal Live
    @GET("api/v1/mobilenotifikasi")
    suspend fun getJadwalLive(): Response<ApiLiveResponse>

    // Tambahkan bookmark baru
    @POST("api/v1/mobilebookmark/")
    suspend fun addBookmark(@Body request: BookmarkRequest): Response<GenericResponse>

    // Toggle bookmark
    @POST("api/v1/mobilebookmark/toggle")
    suspend fun toggleBookmark(@Body request: BookmarkRequest): Response<ToggleResponse>

    // Ambil semua bookmark berdasarkan email_user
    @GET("api/v1/mobilebookmark/{email_user}")
    suspend fun getBookmarks(@Path("email_user") emailUser: String): Response<ApiResponse>

    // Hapus semua bookmark berdasarkan email_user
    @DELETE("api/v1/mobilebookmark/{email_user}")
    suspend fun deleteAllBookmarks(@Path("email_user") emailUser: String): Response<GenericResponse>

    // Hapus bookmark tertentu berdasarkan email_user dan kategori_id
    @POST("api/v1/mobilebookmark/delete")
    suspend fun deleteSpecificBookmark(@Body request: BookmarkRequest): Response<GenericResponse>
}