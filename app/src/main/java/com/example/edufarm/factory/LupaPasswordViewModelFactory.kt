package com.example.edufarm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.edufarm.data.repository.AuthRepository
import com.example.edufarm.viewModel.LupaPasswordViewModel

class LupaPasswordViewModelFactory(private val repository: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LupaPasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LupaPasswordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}