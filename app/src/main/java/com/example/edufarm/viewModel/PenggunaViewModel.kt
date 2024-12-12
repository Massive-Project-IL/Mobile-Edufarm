package com.example.edufarm.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.dataStore.getCurrentUserEmail
import com.example.edufarm.data.dataStore.getToken
import com.example.edufarm.data.model.PasswordUpdateRequest
import com.example.edufarm.data.model.Pengguna
import com.example.edufarm.data.repository.PenggunaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PenggunaViewModel(
    private val repository: PenggunaRepository,
    application: Application
) : AndroidViewModel(application) {

    // State untuk get Pengguna
    private val _penggunaState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val penggunaState: StateFlow<ProfileState> get() = _penggunaState

    // State untuk Update Pengguna
    private val _editPenggunaState = MutableStateFlow<EditProfileState>(EditProfileState.Idle)
    val editPenggunaState: StateFlow<EditProfileState> get() = _editPenggunaState

    // State untuk update password
    private val _updatePasswordState =
        MutableStateFlow<UpdatePasswordState>(UpdatePasswordState.Idle)
    val updatePasswordState: StateFlow<UpdatePasswordState> = _updatePasswordState

    fun getPengguna() {
        viewModelScope.launch {
            _penggunaState.value = ProfileState.Loading
            try {
                val email = getCurrentUserEmail(getApplication<Application>().applicationContext)
                if (email.isNullOrEmpty()) {
                    _penggunaState.value =
                        ProfileState.Error("Email pengguna aktif tidak ditemukan")
                    return@launch
                }

                val token = getToken(getApplication<Application>().applicationContext, email)
                if (!token.isNullOrEmpty()) {
                    val pengguna = repository.getPengguna("Bearer $token")
                    _penggunaState.value = ProfileState.Success(pengguna)
                } else {
                    _penggunaState.value = ProfileState.Error("Token tidak ditemukan untuk $email")
                }
            } catch (e: Exception) {
                Log.e("PenggunaViewModel", "Kesalahan saat memuat data pengguna: ${e.message}", e)
                _penggunaState.value = ProfileState.Error("Kesalahan: ${e.message}")
            }
        }
    }

    fun editPengguna(updatedProfile: Pengguna) {
        viewModelScope.launch {
            _editPenggunaState.value = EditProfileState.Loading
            try {
                val email = getCurrentUserEmail(getApplication<Application>().applicationContext)
                if (email.isNullOrEmpty()) {
                    _editPenggunaState.value =
                        EditProfileState.Error("Email pengguna aktif tidak ditemukan")
                    return@launch
                }

                val token = getToken(getApplication<Application>().applicationContext, email)
                if (!token.isNullOrEmpty()) {
                    val successMessage = repository.editPengguna("Bearer $token", updatedProfile)
                    _editPenggunaState.value = EditProfileState.SuccessMessage(successMessage)
                } else {
                    _editPenggunaState.value = EditProfileState.Error("Token tidak ditemukan")
                }
            } catch (e: Exception) {
                _editPenggunaState.value = EditProfileState.Error("Kesalahan: ${e.message}")
            }
        }
    }

    fun updatePassword(passwordUpdateRequest: PasswordUpdateRequest) {
        viewModelScope.launch {
            _updatePasswordState.value = UpdatePasswordState.Loading
            try {
                // Validasi input
                if (passwordUpdateRequest.oldPassword.isEmpty()) {
                    _updatePasswordState.value =
                        UpdatePasswordState.Error("Kata sandi lama harus diisi.")
                    return@launch
                }
                if (passwordUpdateRequest.newPassword.length < 8) {
                    _updatePasswordState.value =
                        UpdatePasswordState.Error("Kata sandi baru harus minimal 8 karakter.")
                    return@launch
                }
                if (passwordUpdateRequest.newPassword != passwordUpdateRequest.confirmPassword) {
                    _updatePasswordState.value =
                        UpdatePasswordState.Error("Konfirmasi kata sandi tidak sesuai.")
                    return@launch
                }

                // Ambil email pengguna aktif dari DataStore
                val email = getEmailUser()
                if (email.isNullOrEmpty()) {
                    _updatePasswordState.value =
                        UpdatePasswordState.Error("Email pengguna tidak ditemukan")
                    return@launch
                }

                // Buat salinan PasswordUpdateRequest dengan email pengguna
                val updatedRequest = passwordUpdateRequest.copy(email_user = email)

                // Ambil token dari DataStore
                val token = getToken(getApplication<Application>().applicationContext, email)
                if (token.isNullOrEmpty()) {
                    _updatePasswordState.value = UpdatePasswordState.Error("Token tidak ditemukan")
                    return@launch
                }

                // Kirim permintaan ke repository
                val authorizationHeader = "Bearer $token"
                Log.d("UpdatePassword", "Header Authorization: $authorizationHeader")
                Log.d("UpdatePassword", "Body Request: $updatedRequest")

                val response = repository.updatePassword(authorizationHeader, updatedRequest)
                _updatePasswordState.value = UpdatePasswordState.Success(response.msg)
            } catch (e: Exception) {
                Log.e("UpdatePassword", "Kesalahan: ${e.message}", e)
                val errorMessage = when {
                    e.message?.contains("403") == true -> "Kata sandi lama tidak ditemukan."
                    e.message?.contains("400") == true -> "Kata sandi lama salah."
                    else -> e.message ?: "Terjadi kesalahan."
                }
                _updatePasswordState.value = UpdatePasswordState.Error(errorMessage)
            }
        }
    }

    fun getEmailUser(): String? {
        return runBlocking {
            getCurrentUserEmail(getApplication<Application>().applicationContext)
        }
    }
}


sealed class UpdatePasswordState {
    object Idle : UpdatePasswordState()
    object Loading : UpdatePasswordState()
    data class Success(val message: String) : UpdatePasswordState()
    data class Error(val message: String) : UpdatePasswordState()
}

sealed class EditProfileState {
    object Idle : EditProfileState()
    object Loading : EditProfileState()
    data class Success(val pengguna: Pengguna?) : EditProfileState()
    data class SuccessMessage(val message: String) : EditProfileState() // Tambahkan ini
    data class Error(val message: String) : EditProfileState()
}

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val pengguna: Pengguna) :
        ProfileState() // Ubah ke `Pengguna` tunggal jika hanya satu pengguna

    data class Error(val message: String) : ProfileState()
}
