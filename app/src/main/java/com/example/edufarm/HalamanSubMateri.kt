package com.example.edufarm

import android.net.Uri
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import coil.compose.rememberAsyncImagePainter
import com.example.edufarm.data.model.Materi
import com.example.edufarm.ui.components.SearchBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.MateriViewModel


@Composable
fun SubMateriScreen(
    navController: NavController,
    kategoriId: Int,
    viewModel: MateriViewModel = viewModel()
) {
    val listOfMateri by viewModel.materiList.collectAsState()
    val filteredMateri = listOfMateri.filter { it.kategori_id == kategoriId } // Filter modul berdasarkan kategoriId
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(kategoriId) {
        // Pastikan memuat data sesuai kategoriId
        viewModel.fetchMateriByCategory(kategoriId)
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

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Terjadi kesalahan.",
                color = MaterialTheme.colorScheme.error
            )
        }

        LazyColumn {
            items(filteredMateri) { materi -> // Menggunakan filteredMateri yang sudah difilter
                MateriCard(materi = materi, navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun MateriCard(
    materi: Materi, // Menggunakan objek Materi untuk mengambil data
    navController: NavController // Untuk navigasi ke halaman detail materi
) {
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
                // Menentukan gambar untuk materi
                val gambar = if (materi.gambar.isNullOrEmpty()) {
                    // Gambar fallback jika materi.gambar null atau kosong
                    painterResource(id = R.drawable.petani)
                } else {
                    rememberAsyncImagePainter(materi.gambar)
                }

                Image(
                    painter = gambar,
                    contentDescription = "Image for ${materi.nama_modul}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 120.dp, height = 115.dp)
                        .clip(RoundedCornerShape(14.dp)) // Membulatkan gambar
                )
                Spacer(modifier = Modifier.width(14.dp))

                // Kolom untuk informasi materi
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Nama Materi
                    Text(
                        text = materi.nama_modul,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Baris untuk tombol dan centang
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                // Navigasi ke halaman detail materi
                                navController.navigate("halamanIsiMateri/${materi.modul_id}/${Uri.encode(materi.nama_modul)}")
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green)),
                            contentPadding = PaddingValues(horizontal = 3.dp, vertical = 0.dp),
                            modifier = Modifier
                                .width(95.dp)
                                .height(24.dp)
                        ) {
                            Text(
                                text = "Lihat Materi",
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                fontSize = 12.sp
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
            kategoriId = 1,
            navController = rememberNavController()
        )
    }
}
