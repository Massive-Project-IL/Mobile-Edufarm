package com.example.edufarm.data.repository

import com.example.edufarm.data.model.Materi

interface MateriRepository {
    fun getMateriList(): List<Materi>
}
