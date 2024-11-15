package com.example.edufarm.data.repository

import com.example.edufarm.R
import com.example.edufarm.data.model.Materi

class DummyMateriRepository : MateriRepository {
    override fun getMateriList(): List<Materi> {
        return listOf(
            Materi(1, "Pemilihan Benih Kacang Tanah", R.drawable.image_1, "Ayo, Belajar"),
            Materi(2, "Persiapan Tanah Kacang Tanah", R.drawable.image_2, "Ayo, Belajar"),
            Materi(3, "Pengendalian Hama Kacang Tanah", R.drawable.image_3, "Ayo, Belajar"),
            Materi(4, "Panen Tanaman Kacang Tanah", R.drawable.image_4, "Ayo, Belajar"),
            Materi(5, "Video Tutorial Penanaman Kacang Tanah", R.drawable.image_5, "Tonton Video"),
            Materi(6, "Dokumen Tambahan : Penanaman Kacang Tanah", R.drawable.image_6, "Download")
        )
    }
}

