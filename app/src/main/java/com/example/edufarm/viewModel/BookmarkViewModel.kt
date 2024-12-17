package com.example.edufarm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.edufarm.data.dataStore.getCurrentUserEmail
import com.example.edufarm.data.dataStore.loadBookmarkStatus
import com.example.edufarm.data.dataStore.saveBookmarkStatus
import com.example.edufarm.data.model.Bookmark
import com.example.edufarm.data.model.BookmarkRequest
import com.example.edufarm.data.repository.BookmarkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repository: BookmarkRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> get() = _statusMessage

    private val _bookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val bookmarks: StateFlow<List<Bookmark>> get() = _bookmarks

    private val _isBookmarkedMap = MutableStateFlow<MutableMap<Int, Boolean>>(mutableMapOf())
    val isBookmarkedMap: StateFlow<MutableMap<Int, Boolean>> get() = _isBookmarkedMap

    private val _currentUserEmail = MutableStateFlow<String?>(null)
    val currentUserEmail: StateFlow<String?> get() = _currentUserEmail

    init {
        viewModelScope.launch {
            _currentUserEmail.value = getCurrentUserEmail(application.applicationContext)
            _currentUserEmail.value?.let { fetchBookmarks(it) }
        }
        loadBookmarksFromLocal()
    }

    fun getBookmarks() {
        val emailUser = _currentUserEmail.value
        if (emailUser != null) {
            viewModelScope.launch {
                try {
                    val response = repository.getBookmarks(emailUser)
                    if (response.isSuccessful) {
                        val apiResponse = response.body() // Mendapatkan respons yang dikembalikan oleh API
                        _bookmarks.value = apiResponse?.data ?: emptyList() // Ambil data bookmark
                    } else {
                        _statusMessage.value = "Error: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _statusMessage.value = "Error: ${e.message}"
                }
            }
        } else {
            _statusMessage.value = "User not logged in."
        }
    }



    fun addBookmark(kategoriId: Int) {
        val emailUser = _currentUserEmail.value
        if (emailUser != null) {
            viewModelScope.launch {
                try {
                    val response = repository.addBookmark(BookmarkRequest(emailUser, kategoriId))
                    if (response.isSuccessful) {
                        _statusMessage.value = response.body()?.message
                        fetchBookmarks(emailUser)
                    } else {
                        _statusMessage.value = "Error: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _statusMessage.value = "Error: ${e.message}"
                }
            }
        } else {
            _statusMessage.value = "User not logged in."
        }
    }

    fun toggleBookmark(kategoriId: Int) {
        val emailUser = _currentUserEmail.value
        if (emailUser != null) {
            viewModelScope.launch {
                try {
                    val response = repository.toggleBookmark(BookmarkRequest(emailUser, kategoriId))
                    if (response.isSuccessful) {
                        val updatedMap = _isBookmarkedMap.value.toMutableMap()
                        response.body()?.let { toggleResponse ->
                            updatedMap[kategoriId] = toggleResponse.isBookmarked
                        }
                        _isBookmarkedMap.value = updatedMap
                        _statusMessage.value = response.body()?.message
                        saveBookmarksToLocal()
                    } else {
                        _statusMessage.value = "Error: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _statusMessage.value = "Error: ${e.message}"
                }
            }
        } else {
            _statusMessage.value = "User not logged in."
        }
    }

    private fun fetchBookmarks(emailUser: String) {
        viewModelScope.launch {
            try {
                val response = repository.getBookmarks(emailUser)
                if (response.isSuccessful) {
                    val apiResponse = response.body() // Mengambil ApiResponse yang berisi 'data'
                    _bookmarks.value = apiResponse?.data ?: emptyList() // Ambil data bookmark dari 'data'
                } else {
                    _statusMessage.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteAllBookmarks() {
        val emailUser = _currentUserEmail.value
        if (emailUser != null) {
            viewModelScope.launch {
                try {
                    val response = repository.deleteAllBookmarks(emailUser)
                    if (response.isSuccessful) {
                        _statusMessage.value = response.body()?.message
                        _bookmarks.value = emptyList()
                        _isBookmarkedMap.value = mutableMapOf()
                        saveBookmarksToLocal()
                    } else {
                        _statusMessage.value = "Error: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _statusMessage.value = "Error: ${e.message}"
                }
            }
        } else {
            _statusMessage.value = "User not logged in."
        }
    }

    fun deleteSpecificBookmark(kategoriId: Int) {
        val emailUser = _currentUserEmail.value
        if (emailUser != null) {
            viewModelScope.launch {
                try {
                    val response = repository.deleteSpecificBookmark(BookmarkRequest(emailUser, kategoriId))
                    if (response.isSuccessful) {
                        _statusMessage.value = response.body()?.message
                        fetchBookmarks(emailUser)
                    } else {
                        _statusMessage.value = "Error: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _statusMessage.value = "Error: ${e.message}"
                }
            }
        } else {
            _statusMessage.value = "User not logged in."
        }
    }

    private fun loadBookmarksFromLocal() {
        viewModelScope.launch {
            val savedBookmarks = loadBookmarkStatus(getApplication<Application>().applicationContext)
            _isBookmarkedMap.value = savedBookmarks.toMutableMap()
        }
    }

    private fun saveBookmarksToLocal() {
        viewModelScope.launch {
            saveBookmarkStatus(getApplication<Application>().applicationContext, _isBookmarkedMap.value)
        }
    }
}

