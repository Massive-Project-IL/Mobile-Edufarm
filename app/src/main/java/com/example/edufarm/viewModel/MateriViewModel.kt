package com.example.edufarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.model.Materi
import com.example.edufarm.data.repository.MateriRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MateriViewModel(private val repository: MateriRepository = MateriRepository()) : ViewModel() {
    private val _materiList = MutableStateFlow<List<Materi>>(emptyList())
    val materiList: StateFlow<List<Materi>> get() = _materiList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchMateriByCategory(kategoriId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getMateriByCategory(kategoriId)
                if (response != null) {
                    _materiList.value = response
                } else {
                    _errorMessage.value = "Gagal memuat data materi."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            }
        }
    }
}
