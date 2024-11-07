package com.example.edufarm.navigation

import android.net.Uri

object Routes {
     const val HALAMAN_SPLASH = "halamanAwal"
     const val HALAMAN_LOGIN  = "halamanLogin"
     const val HALAMAN_DAFTAR = "halamanDaftar"
     const val HALAMAN_BERANDA = "halamanBeranda"
     const val HALAMAN_JADWAL_LIVE = "halamanJadwalLive"
     const val HALAMAN_LIVE_MENTOR = "halamanLiveMentor"
     const val HALAMAN_BOOKMARK = "halamanBookmark"
     const val HALAMAN_AKUN = "halamanAkun"
     const val HALAMAN_NOTIFIKASI_DAFTAR = "halamanNotifikasiDaftar"
     const val HALAMAN_PELATIHAN = "halamanPelatihan"
     const val HALAMAN_SUB_MATERI = "halamanSubMateri"
     const val HALAMAN_ISI_MATERI = "halamanIsiMateri/{id}/{title}"
     const val HALAMAN_MATERI_VIDEO = "halamanMateriVideo/{videoUri}"
     const val HALAMAN_MATERI_DOKUMEN = "halamanMateriDokumen/{id}/{title}"

     // Fungsi untuk membangun rute dinamis
     fun getHalamanIsiMateriRoute(id: Int, title: String): String {
          return "halamanIsiMateri/$id/${Uri.encode(title)}"
     }

     fun getHalamanMateriVideoRoute(videoUri: String): String {
          return "halamanMateriVideo/${Uri.encode(videoUri)}"
     }

     fun getHalamanMateriDokumenRoute(id: Int, title: String): String {
          return "halamanMateriDokumen/$id/${Uri.encode(title)}"
     }
}