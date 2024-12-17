package com.example.edufarm.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.edufarm.data.repository.BookmarkRepository
import com.example.edufarm.viewModel.BookmarkViewModel

class BookmarkViewModelFactory(
    private val repository: BookmarkRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarkViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
