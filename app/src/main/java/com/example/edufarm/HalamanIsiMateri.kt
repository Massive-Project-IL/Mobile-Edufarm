package com.example.edufarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.MateriViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun IsiMateriScreen(navController: NavController, kategoriId: Int, modulId: Int) {
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    val viewModel: MateriViewModel = viewModel()
    val materiList = viewModel.materiList.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    LaunchedEffect(kategoriId) {
        viewModel.fetchMateriByCategory(kategoriId)
        systemUiController.setStatusBarColor(color = topBarColor, darkIcons = true)
    }

    val currentMateri = materiList.find { it.modul_id == modulId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
    ) {
        TopBar(
            title = "Materi",
            navController = navController,
            modifier = Modifier
                .padding(start = 35.dp, end = 35.dp, top = 5.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        if (currentMateri != null) {
            Box(modifier = Modifier.fillMaxWidth()) {
                currentMateri?.let { materi ->
                    materi.gambar?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = "Gambar Materi",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 35.dp)
            ) {
                Text(
                    text = currentMateri.nama_modul,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(R.color.black)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = currentMateri.text_module ?: "Tidak ada materi",
                    fontSize = 10.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(R.color.black)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
        } else {
            Text(
                text = errorMessage ?: "Memuat data materi...",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = poppinsFontFamily,
                color = colorResource(R.color.black),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIsiMateriScreen() {
    EdufarmTheme {
        IsiMateriScreen(
            navController = rememberNavController(),
            kategoriId = 1,
            modulId = 1
        )
    }
}