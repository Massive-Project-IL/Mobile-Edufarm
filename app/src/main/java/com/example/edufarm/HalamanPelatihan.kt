package com.example.edufarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.edufarm.data.model.Kategori
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.SearchBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.PelatihanViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PelatihanScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    pelatihanViewModel: PelatihanViewModel = viewModel() // Tambahkan ViewModel
) {
    val pelatihanList by pelatihanViewModel.pelatihanList.collectAsState()
    val errorMessage by pelatihanViewModel.errorMessage.collectAsState()
    val selectedItem = remember { mutableStateOf("Pelatihan") }
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
        pelatihanViewModel.fetchPelatihan() // Memuat data saat screen dibuka
    }

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavigationBar(navController = navController, selectedItem = selectedItem) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.background))
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
            ) {

                Text(
                    text = "Pelatihan",
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                SearchBar(placeholder = "Cari Pelatihan")
//                Text(
//                    text = "Kategori",
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(top = 8.dp)
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
////                KategoriChips() // Tidak diubah
////                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(28.dp))

                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "Error tidak diketahui",
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(pelatihanList) { pelatihan ->
                            CardPelatihanKategori(navController, pelatihan)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardPelatihanKategori(
    navController: NavController,
    pelatihan: Kategori // Menambahkan parameter untuk mengambil data kategori dari backend
) {
    var isBookmarked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = pelatihan.gambar ?: "https://example.com/default_image.jpg",
                        placeholder = painterResource(R.drawable.petani), // Gambar placeholder
                        error = painterResource(R.drawable.petani) // Gambar error jika gagal memuat gambar
                    ),
                    contentDescription = "Deskripsi Gambar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .clip(RoundedCornerShape(16.dp))
                )


                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 19.dp, end = 15.dp)
                        .size(24.dp)
                        .background(
                            color = if (isBookmarked) Color.White else Color.Gray,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable { isBookmarked = !isBookmarked },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.bookmark_green else R.drawable.bookmark_putih
                        ),
                        contentDescription = "Bookmark",
                        modifier = Modifier.size(width = 16.dp, height = 18.dp)
                    )
                }
            }

            // Menampilkan teks dan tombol berdasarkan data dari backend
            Column(modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 8.dp)
            ) {
                Text(
                    text = pelatihan.nama_kategori, // Mengambil nama kategori dari backend
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Materi ini akan membahas cara menanam ${pelatihan.nama_kategori} dari awal sampai akhir", // Mengambil penjelasan kategori dari backend
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    lineHeight = 13.sp,
                    color = colorResource(id = R.color.gray_bookmark),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            // Pastikan kategori_id yang benar dikirim
                            navController.navigate("halamanSubMateri/${pelatihan.kategori_id}")
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                        modifier = Modifier
                            .width(115.dp)
                            .height(30.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Lihat Selengkapnya",
                            fontSize = 10.sp,
                            color = Color.White,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight(500),
                        )
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPelatihanScreen() {
    EdufarmTheme {
        Column (modifier = Modifier.background(colorResource(id = R.color.background))){
            PelatihanScreen(
                navController = rememberNavController()
            )
        }
    }
}

//@Composable
//fun KategoriChips() {
//    val categories = listOf("Kacang Tanah", "Kacang Polong", "Jagung", "Gandum", "Kedelai")
//    var selectedCategory by remember { mutableStateOf(categories[0]) }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .horizontalScroll(rememberScrollState()),
//        horizontalArrangement = Arrangement.spacedBy(14.dp)
//    ) {
//        categories.forEach { category ->
//            Box(
//                modifier = Modifier
//                    .background(
//                        color = if (category == selectedCategory) colorResource(R.color.green)
//                        else colorResource(R.color.white),
//                        shape = RoundedCornerShape(6.dp)
//                    )
//                    .clickable { selectedCategory = category }
//                    .border(
//                        width = 1.dp,
//                        color = colorResource(R.color.green),
//                        shape = RoundedCornerShape(6.dp)
//                    )
//                    .padding(horizontal = 16.dp, vertical = 4.dp)
//            ) {
//                Text(
//                    text = category,
//                    color = if (category == selectedCategory) colorResource(R.color.white)
//                    else colorResource(R.color.gray_bookmark),
//                    fontSize = 10.sp,
//                    fontWeight = FontWeight.Medium,
//                    fontFamily = poppinsFontFamily,
//                    lineHeight = 20.sp,
//                    letterSpacing = (-0.24).sp
//                )
//            }
//        }
//    }
//}