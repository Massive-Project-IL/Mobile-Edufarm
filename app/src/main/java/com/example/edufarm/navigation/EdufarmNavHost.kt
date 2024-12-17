package com.example.edufarm.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.edufarm.akun.HalamanTentangKami
import com.example.edufarm.akun.NotifikasiProfileScreen
import com.example.edufarm.akun.NotifikasiSandiScreen
import com.example.edufarm.akun.ProfileScreen
import com.example.edufarm.akun.UbahSandiScreen
import com.example.edufarm.akun.password.AturUlangSandiScreen
import com.example.edufarm.akun.password.LupaPasswordScreen
import com.example.edufarm.akun.password.NotifikasiPasswordScreen
import com.example.edufarm.akun.password.VerifikasiEmailScreen
import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.repository.PenggunaRepository
import com.example.edufarm.factory.PenggunaViewModelFactory
import com.example.edufarm.viewModel.PenggunaViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EdufarmNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HALAMAN_SPLASH,
        modifier = modifier
    ) {
        composable(Routes.HALAMAN_SPLASH) {
            EduFarmScreen(navController)
        }

        composable(Routes.HALAMAN_LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.HALAMAN_BERANDA) {
            ContentScreen(navController)
        }


        composable(Routes.HALAMAN_DAFTAR) {
            DaftarScreen(navController)
        }

        composable(Routes.HALAMAN_NOTIFIKASI_DAFTAR) {
            NotifikasiDaftarScreen(navController)
        }

        composable(Routes.HALAMAN_LIVE_MENTOR) {
            LiveMentorScreen(navController)
        }

        composable(Routes.HALAMAN_JADWAL_LIVE) {
            JadwalLiveScreen(navController)
        }

        composable(Routes.HALAMAN_PELATIHAN) {
            PelatihanScreen(navController)
        }

        composable(Routes.HALAMAN_BOOKMARK) {
            BookmarkScreen(navController)
        }

        composable(
            route = Routes.HALAMAN_SUB_MATERI,
            arguments = listOf(
                navArgument("kategoriId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val kategoriId = backStackEntry.arguments?.getInt("kategoriId") ?: 0
            SubMateriScreen(
                navController = navController,
                kategoriId = kategoriId // Menyuntikkan kategoriId ke dalam SubMateriScreen
            )
        }

        composable(
            route = "HALAMAN_ISI_MATERI/{kategoriId}/{modulId}",
            arguments = listOf(
                navArgument("kategoriId") { type = NavType.IntType },
                navArgument("modulId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val kategoriId = backStackEntry.arguments?.getInt("kategoriId") ?: 0
            val modulId = backStackEntry.arguments?.getInt("modulId") ?: 0
            IsiMateriScreen(navController = navController, kategoriId = kategoriId, modulId = modulId)
        }

        composable(
            route = "HALAMAN_MATERI_DOKUMEN/{kategoriId}/{modulId}",
            arguments = listOf(
                navArgument("kategoriId") { type = NavType.IntType },
                navArgument("modulId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val kategoriId = backStackEntry.arguments?.getInt("kategoriId") ?: 0
            val modulId = backStackEntry.arguments?.getInt("modulId") ?: 0
            MateriDokumenScreen(navController = navController, kategoriId = kategoriId, modulId = modulId)
        }

        composable(
            route = "HALAMAN_MATERI_VIDEO/{kategoriId}/{modulId}",
            arguments = listOf(
                navArgument("kategoriId") { type = NavType.IntType },
                navArgument("modulId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val kategoriId = backStackEntry.arguments?.getInt("kategoriId") ?: 0
            val modulId = backStackEntry.arguments?.getInt("modulId") ?: 0
            MateriVideoScreen(navController = navController, kategoriId = kategoriId, modulId = modulId)
        }

        // Akun
        composable(Routes.HALAMAN_AKUN) {
            ProfileScreen(navController = navController)
        }

        composable(Routes.HALAMAN_EDIT_PROFILE) {
            val penggunaViewModel: PenggunaViewModel = viewModel(
                factory = PenggunaViewModelFactory(
                    repository = PenggunaRepository(apiService = ApiClient.apiService),
                    application = LocalContext.current.applicationContext as Application
                )
            )

            HalamanEditProfile(
                navController = navController,
                penggunaViewModel = penggunaViewModel
            )
        }

        composable(Routes.NOTIFIKASI_PROFILE) {
            NotifikasiProfileScreen(navController = navController)
        }

        composable(Routes.NOTIFIKASI_SANDI) {
            NotifikasiSandiScreen(navController = navController)
        }


        composable(Routes.HALAMAN_UBAH_SANDI) {
            val penggunaViewModel: PenggunaViewModel = viewModel(
                factory = PenggunaViewModelFactory(
                    repository = PenggunaRepository(apiService = ApiClient.apiService),
                    application = LocalContext.current.applicationContext as Application
                )
            )

            UbahSandiScreen(
                navController = navController,
                penggunaViewModel = penggunaViewModel
            )
        }


        composable(Routes.HALAMAN_TENTANG_KAMI) {
            HalamanTentangKami(navController = navController)
        }

        //LupaKataSandi
        composable(
            route = "halamanAturUlangSandi?email={email}&otp={otp}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("otp") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val otp = backStackEntry.arguments?.getString("otp")
            AturUlangSandiScreen(navController = navController, email = email ?: "", otp = otp ?: "")
        }


        composable(Routes.LUPA_PASSWORD) {
            LupaPasswordScreen(navController = navController)
        }

        composable(Routes.NOTIFIKASI_PASSWORD) {
            NotifikasiPasswordScreen(navController = navController)
        }

        composable("verifikasi_email_screen/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            VerifikasiEmailScreen(navController = navController, email = email ?: "")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewEdufarmNavHost() {
    EdufarmNavHost(navController = rememberNavController())
}

