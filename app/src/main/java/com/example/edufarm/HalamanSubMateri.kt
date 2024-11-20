package com.example.edufarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.data.model.Materi
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.SearchBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewmodel.MateriViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay


@Composable
fun SubMateriScreen(
    navController: NavController,
    viewModel: MateriViewModel = viewModel() // Menggunakan ViewModel
) {
    val listOfMateri = viewModel.materiList
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = false
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(start = 35.dp, end = 35.dp, top = 5.dp)
    ) {
        TopBar(
            navController = navController,
            title = "Materi"
        )
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar(placeholder = "Cari Pelatihan")
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ayo Kita Mulai Belajar",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 23.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(listOfMateri) { materi ->
                MateriCard(materi = materi, navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun MateriCard(
    materi: Materi,
    navController: NavController,
    materiViewModel: MateriViewModel = viewModel()
) {
    val isCompleted = materi.id in materiViewModel.completedMateriIds
    val showCheckmark = remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .padding(start = 18.dp, top = 24.dp, bottom = 24.dp, end = 18.dp)
                    .fillMaxWidth()
                    .height(115.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Gambar Materi
                Image(
                    painter = painterResource(id = materi.imageRes),
                    contentDescription = "Image for ${materi.title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 120.dp, height = 115.dp)
                        .clip(RoundedCornerShape(14.dp))
                )
                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = materi.title,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Row untuk tombol dan centang
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(), // Agar mengisi lebar baris
                        horizontalArrangement = Arrangement.SpaceBetween // Untuk menempatkan tombol di kiri dan centang di kanan
                    ) {
                        Button(
                            onClick = {
                                // Tandai materi sebagai selesai
                                materiViewModel.markAsCompleted(materi.id)

                                // Navigasi ke halaman yang sesuai
                                val route = when (materi.buttonText) {
                                    "Ayo, Belajar" -> Routes.getHalamanIsiMateriRoute(
                                        materi.id,
                                        materi.title
                                    )

                                    "Tonton Video" -> Routes.getHalamanMateriVideoRoute("your_video_uri_here")
                                    "Download" -> Routes.getHalamanMateriDokumenRoute(
                                        materi.id,
                                        materi.title
                                    )

                                    else -> null
                                }
                                route?.let { navController.navigate(it) }

                                // Menunda munculnya centang
                                showCheckmark.value = true
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green)),
                            contentPadding = PaddingValues(horizontal = 3.dp, vertical = 0.dp),
                            modifier = Modifier
                                .width(95.dp)
                                .height(24.dp)
                        ) {
                            Text(
                                text = materi.buttonText,
                                fontWeight = FontWeight.Medium,
                                fontFamily = poppinsFontFamily,
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Menunda munculnya centang
                        LaunchedEffect(isCompleted) {
                            if (isCompleted) {
                                delay(10000) // Menunggu 500ms sebelum menunjukkan centang
                                showCheckmark.value = true
                            }
                        }

                        if (isCompleted) {
                            Icon(
                                painter = painterResource(R.drawable.check_circle),
                                contentDescription = "Completed",
                                tint = colorResource(R.color.green),
                                modifier = Modifier
                                    .size(22.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun PreviewSubMateriScreen() {
    EdufarmTheme {
        SubMateriScreen(
            navController = rememberNavController()
        )
    }
}
