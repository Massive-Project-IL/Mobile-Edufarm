package com.example.edufarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.model.RegisterRequest
import com.example.edufarm.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> get() = _registerState

    // Fungsi untuk melakukan pendaftaran pengguna
    fun registerUser(email: String, password: String, nama: String, telpon: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading

            try {
                val response = repository.registerUser(
                    RegisterRequest(
                        email_user = email,
                        password = password,
                        nama_user = nama,
                        telpon_user = telpon
                    )
                )
                if (response.isSuccessful) {
                    _registerState.value = RegisterState.Success(response.body()?.msg ?: "Pendaftaran berhasil")
                } else {
                    // Tangani error BadRequest (email sudah terdaftar)
                    if (response.code() == 400 && response.message().contains("Bad Request", true)) {
                        _registerState.value = RegisterState.Error("Email sudah terdaftar, silakan gunakan email lain")
                    } else {
                        _registerState.value = RegisterState.Error("Pendaftaran gagal: ${response.message()}")
                    }
                }
            } catch (e: HttpException) {
                _registerState.value = RegisterState.Error("Terjadi kesalahan: ${e.message()}")
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Terjadi kesalahan tidak diketahui: ${e.message}")
            }
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}