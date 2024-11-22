package com.example.edufarm.data.source.lokal

import com.example.edufarm.data.model.Jadwal

object DummyData {
    val jadwalPerHari = mapOf(
        "Senin" to listOf(
            Jadwal(
                id = 1,
                title = "Bertanam Gandum",
                timeRange = "09:30:00 - 12:30:00",
                mentor = "Vodka"
            ),
            Jadwal(
                id = 2,
                title = "Menanam Padi",
                timeRange = "13:00:00 - 15:00:00",
                mentor = "Siti"
            )
        ),
        "Selasa" to listOf(
            Jadwal(
                id = 3,
                title = "Budidaya Jagung",
                timeRange = "16:00:00 - 18:00:00",
                mentor = "Ahmad"
            )
        ),
        "Rabu" to listOf(
            Jadwal(
                id = 4,
                title = "Menanam Sayur",
                timeRange = "10:00:00 - 12:00:00",
                mentor = "Dewi"
            )
        ),
        "Kamis" to emptyList(), // Tidak ada jadwal
        "Jumat" to listOf(
            Jadwal(
                id = 5,
                title = "Belajar Hidroponik",
                timeRange = "14:00:00 - 16:00:00",
                mentor = "Arif"
            )
        ),
        "Sabtu" to listOf(
            Jadwal(
                id = 6,
                title = "Budidaya Buah",
                timeRange = "09:00:00 - 11:30:00",
                mentor = "Santi"
            )
        ),
        "Minggu" to listOf(
            Jadwal(
                id = 7,
                title = "Pengenalan Teknologi Pertanian",
                timeRange = "08:00:00 - 10:00:00",
                mentor = "Budi"
            )
        )
    )
}
