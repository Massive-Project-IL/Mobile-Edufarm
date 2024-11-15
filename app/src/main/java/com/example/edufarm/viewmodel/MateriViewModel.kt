package com.example.edufarm.viewmodel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.edufarm.data.model.Materi
import com.example.edufarm.data.repository.DummyMateriRepository
import com.example.edufarm.data.repository.MateriRepository

class MateriViewModel : ViewModel() {
    private val repository: MateriRepository = DummyMateriRepository()

    val materiList: List<Materi> = repository.getMateriList()

    private val _completedMateriIds = mutableStateListOf<Int>()
    val completedMateriIds: List<Int> get() = _completedMateriIds

    fun markAsCompleted(materiId: Int) {
        if (materiId !in _completedMateriIds) {
            _completedMateriIds.add(materiId)
        }
    }
}
