package com.example.edufarm.navigation

object Routes {
    const val HALAMAN_SPLASH = "halamanAwal"

    // Login dan Daftar
    const val HALAMAN_LOGIN = "halamanLogin"
    const val HALAMAN_DAFTAR = "halamanDaftar"
    const val HALAMAN_NOTIFIKASI_DAFTAR = "halamanNotifikasiDaftar"


    const val HALAMAN_BERANDA = "halamanBeranda"
    const val HALAMAN_JADWAL_LIVE = "halamanJadwalLive"
    const val HALAMAN_LIVE_MENTOR = "halamanLiveMentor"
    const val HALAMAN_BOOKMARK = "halamanBookmark"
    const val HALAMAN_PELATIHAN = "halamanPelatihan"
    const val HALAMAN_SUB_MATERI = "halamanSubMateri/{kategoriId}"
    const val HALAMAN_ISI_MATERI = "halamanIsiMateri/{id}/{title}"
    const val HALAMAN_MATERI_VIDEO = "halamanMateriVideo/{videoUri}"
    const val HALAMAN_MATERI_DOKUMEN = "halamanMateriDokumen/{id}/{title}"

    // Akun
    const val HALAMAN_AKUN = "halamanAkun"
    const val HALAMAN_UBAH_SANDI = "halamanUbahSandi"
    const val HALAMAN_EDIT_PROFILE = "halamanEditProfile"
    const val HALAMAN_TENTANG_KAMI = "halamanTentangKami"
    const val NOTIFIKASI_PROFILE= "halamanNotifikasiProfile"
    const val NOTIFIKASI_SANDI= "halamanNotifikasiSandi"

    //Lupa Kata Sandi
    const val ATUR_ULANG_SANDI = "halamanAturUlangSandi"
    const val LUPA_PASSWORD = "halamanLupaPassword"
    const val NOTIFIKASI_PASSWORD = "halamanNotifikasiPassword"
    const val VERIFIKASI_EMAIL = "halamanVerifikasiEmail"

}
