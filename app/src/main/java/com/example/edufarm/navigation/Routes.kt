package com.example.edufarm.navigation

import android.net.Uri

object Routes {
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