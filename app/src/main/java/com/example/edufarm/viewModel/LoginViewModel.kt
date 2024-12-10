package com.example.edufarm.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.dataStore.saveCurrentUserEmail
import com.example.edufarm.data.dataStore.saveToken
import com.example.edufarm.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    val body = response.body()
                    val token = body?.token

                    if (token != null) {
                        // Simpan token berdasarkan email input
                        saveToken(getApplication<Application>().applicationContext, email, token)
                        saveCurrentUserEmail(getApplication<Application>().applicationContext, email)
                        Log.d("Login", "Login berhasil untuk $email dengan token: $token")

                        _loginState.value = LoginState.Success(token)
                    } else {
                        _loginState.value = LoginState.Error("Token tidak ditemukan dalam response")
                    }
                } else {
                    _loginState.value = LoginState.Error("Login gagal: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Terjadi kesalahan: ${e.localizedMessage}")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}
