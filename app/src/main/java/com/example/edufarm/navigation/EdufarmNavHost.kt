package com.example.edufarm.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.edufarm.BookmarkScreen
import com.example.edufarm.ContentScreen
import com.example.edufarm.DaftarScreen
import com.example.edufarm.EduFarmScreen
import com.example.edufarm.IsiMateriScreen
import com.example.edufarm.JadwalLiveScreen
import com.example.edufarm.LiveMentorScreen
import com.example.edufarm.LoginScreen
import com.example.edufarm.MateriDokumenScreen
import com.example.edufarm.MateriVideoScreen
import com.example.edufarm.NotifikasiDaftarScreen
import com.example.edufarm.PelatihanScreen
import com.example.edufarm.SubMateriScreen
import com.example.edufarm.akun.HalamanEditProfile
import com.example.edufarm.akun.ProfileScreen
import com.example.edufarm.akun.UbahSandiScreen

@Composable
fun EdufarmNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HALAMAN_SPLASH, // Membuat halaman splash di awal
        modifier = modifier
    ) {
        composable(Routes.HALAMAN_SPLASH) {
            EduFarmScreen(navController)
        }

        composable(Routes.HALAMAN_LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.HALAMAN_DAFTAR) {
            DaftarScreen(navController)
        }

        composable(Routes.HALAMAN_NOTIFIKASI_DAFTAR) {
            NotifikasiDaftarScreen(navController)
        }

        composable(Routes.HALAMAN_BERANDA) {
            ContentScreen(navController, modifier)
        }

        composable(Routes.HALAMAN_LIVE_MENTOR) {
            LiveMentorScreen(navController)
        }

        composable(Routes.HALAMAN_JADWAL_LIVE) {
            JadwalLiveScreen(navController, modifier)
        }

        composable(Routes.HALAMAN_PELATIHAN) {
            PelatihanScreen(navController)
        }

        composable(Routes.HALAMAN_BOOKMARK) {
            BookmarkScreen(navController = navController)
        }

        composable(Routes.HALAMAN_SUB_MATERI) {
            SubMateriScreen(navController)
        }

        composable(
            route = Routes.HALAMAN_ISI_MATERI,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: ""
            IsiMateriScreen(id = id, title = title, navController = navController)
        }


        composable(
            route = Routes.HALAMAN_MATERI_VIDEO,
            arguments = listOf(
                navArgument("videoUri") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val videoUri = Uri.parse(backStackEntry.arguments?.getString("videoUri"))
            MateriVideoScreen(navController = navController, videoUri = videoUri)
        }

        composable(
            route = Routes.HALAMAN_MATERI_DOKUMEN,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            val title = backStackEntry.arguments?.getString("title")
            MateriDokumenScreen(id = id, title = title, navController = navController)
        }


        composable(Routes.HALAMAN_AKUN) {
            ProfileScreen(navController = navController)
        }

        composable(Routes.HALAMAN_EDIT_PROFILE) {
            HalamanEditProfile(navController = navController)
        }

        composable(Routes.HALAMAN_UBAH_SANDI) {
            UbahSandiScreen(navController = navController)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEdufarmNavHost() {
    EdufarmNavHost(navController = rememberNavController())
}

