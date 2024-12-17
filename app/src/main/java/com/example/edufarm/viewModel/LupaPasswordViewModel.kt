package com.example.edufarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LupaPasswordState {
    object Idle : LupaPasswordState()
    object Loading : LupaPasswordState()
    data class Success(val message: String) : LupaPasswordState()
    data class Error(val message: String) : LupaPasswordState()
}

class LupaPasswordViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _otpState = MutableStateFlow<LupaPasswordState>(LupaPasswordState.Idle)
    val otpState: StateFlow<LupaPasswordState> get() = _otpState

    private val _resetState = MutableStateFlow<LupaPasswordState>(LupaPasswordState.Idle)
    val resetState: StateFlow<LupaPasswordState> get() = _resetState

    // Fungsi untuk mengirim OTP ke email
    fun kirimOtp(email: String) {
        viewModelScope.launch {
            _otpState.value = LupaPasswordState.Loading
            try {
                val response = repository.requestOtp(email)
                if (response.isSuccessful) {
                    _otpState.value = LupaPasswordState.Success(response.body()?.msg ?: "Kode OTP telah dikirim")
                } else {
                    _otpState.value = LupaPasswordState.Error("Gagal: ${response.message()}")
                }
            } catch (e: Exception) {
                _otpState.value = LupaPasswordState.Error("Kesalahan: ${e.message}")
            }
        }
    }

    // Fungsi untuk verifikasi OTP
    fun verifikasiOtp(email: String, otp: String) {
        viewModelScope.launch {
            _resetState.value = LupaPasswordState.Loading
            try {
                val response = repository.verifikasiOtp(email, otp)
                if (response.isSuccessful) {
                    _resetState.value = LupaPasswordState.Success(response.body()?.msg ?: "OTP valid")
                } else {
                    _resetState.value = LupaPasswordState.Error("Gagal: ${response.message()}")
                }
            } catch (e: Exception) {
                _resetState.value = LupaPasswordState.Error("Kesalahan: ${e.message}")
            }
        }
    }

    // Fungsi untuk reset password menggunakan OTP
    fun resetPassword(email: String, otp: String, passwordBaru: String, konfirmasiPassword: String) {
        viewModelScope.launch {
            _resetState.value = LupaPasswordState.Loading
            try {
                val response = repository.resetPassword(email, otp, passwordBaru, konfirmasiPassword)
                if (response.isSuccessful) {
                    _resetState.value = LupaPasswordState.Success(response.body()?.msg ?: "Password berhasil direset")
                } else {
                    _resetState.value = LupaPasswordState.Error("Gagal: ${response.message()}")
                }
            } catch (e: Exception) {
                _resetState.value = LupaPasswordState.Error("Kesalahan: ${e.message}")
            }
        }
    }
}


