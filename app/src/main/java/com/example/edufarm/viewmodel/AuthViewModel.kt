package com.example.edufarm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.model.LoginResponse
import com.example.edufarm.data.repository.AuthRepository
import com.example.edufarm.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<LoginResponse>>(Result.Loading)
    val loginState: StateFlow<Result<LoginResponse>> = _loginState

    val isLoading = MutableStateFlow(false)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            Log.d("AuthViewModel", "Memulai proses login...")
            isLoading.value = true
            _loginState.value = Result.Loading
            try {
                val response = repository.login(email, password)
                Log.d("AuthViewModel", "Login berhasil: $response")
                _loginState.value = Result.Success(response)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login gagal: ${e.message}")
                _loginState.value = Result.Failure(e)
            } finally {
                isLoading.value = false
                Log.d("AuthViewModel", "Proses login selesai.")
            }
        }
    }
}

