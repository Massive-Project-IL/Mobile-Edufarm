package com.example.edufarm.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.dataStore.getCurrentUserEmail
import com.example.edufarm.data.dataStore.getToken
import com.example.edufarm.data.model.Pengguna
import com.example.edufarm.data.repository.PenggunaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PenggunaViewModel(
    private val repository: PenggunaRepository,
    application: Application // Gunakan Application untuk akses Context
) : AndroidViewModel(application) {

    private val _penggunaState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val penggunaState: StateFlow<ProfileState> get() = _penggunaState

    private val _editPenggunaState = MutableStateFlow<EditProfileState>(EditProfileState.Idle)
    val editPenggunaState: StateFlow<EditProfileState> get() = _editPenggunaState

    fun getPengguna() {
        viewModelScope.launch {
            _penggunaState.value = ProfileState.Loading
            try {
                val email = getCurrentUserEmail(getApplication<Application>().applicationContext)
                if (email.isNullOrEmpty()) {
                    _penggunaState.value = ProfileState.Error("Email pengguna aktif tidak ditemukan")
                    return@launch
                }

                val token = getToken(getApplication<Application>().applicationContext, email)
                if (!token.isNullOrEmpty()) {
                    val response = repository.getPengguna("Bearer $token")
                    _penggunaState.value = ProfileState.Success(response)
                } else {
                    _penggunaState.value = ProfileState.Error("Token tidak ditemukan untuk $email")
                }
            } catch (e: Exception) {
                _penggunaState.value = ProfileState.Error("Kesalahan: ${e.message}")
            }
        }
    }


    fun editPengguna(updatedProfile: Pengguna) {
        viewModelScope.launch {
            _editPenggunaState.value = EditProfileState.Loading
            try {
                // Ambil email pengguna aktif
                val email = getCurrentUserEmail(getApplication<Application>().applicationContext)
                if (email.isNullOrEmpty()) {
                    _editPenggunaState.value = EditProfileState.Error("Email pengguna aktif tidak ditemukan")
                    return@launch
                }

                // Ambil token menggunakan email
                val token = getToken(getApplication<Application>().applicationContext, email)
                Log.d("PenggunaViewModel", "Token digunakan untuk update: $token")

                if (!token.isNullOrEmpty()) {
                    // Perbarui data pengguna
                    val pengguna = repository.editPengguna("Bearer $token", updatedProfile)
                    if (pengguna.nama_user.isNullOrEmpty() || pengguna.email_user.isNullOrEmpty()) {
                        _editPenggunaState.value = EditProfileState.Error("Data pengguna tidak valid")
                    } else {
                        _editPenggunaState.value = EditProfileState.Success(pengguna)
                    }
                } else {
                    _editPenggunaState.value = EditProfileState.Error("Token tidak ditemukan")
                }
            } catch (e: Exception) {
                Log.e("PenggunaViewModel", "Error updating user data: ${e.message}", e)
                _editPenggunaState.value = EditProfileState.Error("Kesalahan: ${e.message}")
            }
        }
    }

}

// State untuk UI (Edit Profil)
sealed class EditProfileState {
    object Idle : EditProfileState() // Ketika tidak ada proses berlangsung
    object Loading : EditProfileState()
    data class Success(val pengguna: Pengguna) : EditProfileState()
    data class Error(val message: String) : EditProfileState()
}


// State untuk UI
sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val pengguna: Pengguna) : ProfileState() // Ubah ke `Pengguna` tunggal jika hanya satu pengguna
    data class Error(val message: String) : ProfileState()
}
