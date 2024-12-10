package com.example.edufarm.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.edufarm.data.repository.PenggunaRepository
import com.example.edufarm.viewModel.PenggunaViewModel

class PenggunaViewModelFactory(
    private val repository: PenggunaRepository,
    private val application: Application // Pass Application di sini
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PenggunaViewModel::class.java)) {
            return PenggunaViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


