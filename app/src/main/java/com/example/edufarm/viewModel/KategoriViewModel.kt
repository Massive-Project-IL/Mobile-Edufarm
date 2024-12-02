package com.example.edufarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.model.Kategori
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PelatihanViewModel : ViewModel() {
    private val _pelatihanList = MutableStateFlow<List<Kategori>>(emptyList())
    val pelatihanList: StateFlow<List<Kategori>> get() = _pelatihanList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchPelatihan() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getKategori()
                if (response.isSuccessful && response.body()?.success == true) {
                    _pelatihanList.value = response.body()?.data ?: emptyList()
                } else {
                    _errorMessage.value = "Gagal memuat data pelatihan."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            }
        }
    }
}