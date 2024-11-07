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
import com.example.edufarm.IsiMateriScreen
import com.example.edufarm.MateriDokumenScreen
import com.example.edufarm.MateriVideoScreen
import com.example.edufarm.SubMateriScreen

@Composable
fun EdufarmNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HALAMAN_SUB_MATERI,
        modifier = modifier
    ) {
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
            val id = backStackEntry.arguments?.getInt("id")
            val title = backStackEntry.arguments?.getString("title")
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
        // Halaman Materi Dokumen
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEdufarmNavHost() {
    EdufarmNavHost(navController = rememberNavController())
}

