package com.example.edufarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.model.JadwalLive
import com.example.edufarm.data.repository.JadwalLiveRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JadwalLiveViewModel(private val repository: JadwalLiveRepository = JadwalLiveRepository()) : ViewModel() {

    private val _jadwalLive = MutableStateFlow<List<JadwalLive>>(emptyList())
    val jadwalLive: StateFlow<List<JadwalLive>> get() = _jadwalLive

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchJadwalLive() {
        viewModelScope.launch {
            try {
                val response = repository.getJadwalLive()
                if (response != null) {
                    _jadwalLive.value = response
                } else {
                    _errorMessage.value = "Gagal memuat data jadwal live."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            }
        }
    }
}