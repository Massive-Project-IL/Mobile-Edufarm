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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun ContentScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val selectedItem = remember { mutableStateOf("Beranda") } // Menyimpan item yang dipilih

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            InfoCard(
                hai = "Hai,",
                title = "Petani👋",
                deskripsi = "Ayo kita belajar bertani bersama!"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CardLive()
            KategoriBertani()
            Spacer(modifier = Modifier.height(10.dp))
            SelectKategori()
            Spacer(modifier = Modifier.height(10.dp))
            RekomendasiPelatihan()
            LazyColumn(
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(5) {
                    CardPelatihan(navController)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun CardLive() {
    var showDialog by remember { mutableStateOf(false) }
    var isNotificationActive by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 37.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_notif))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Bertanam Gandum",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.green_title)
                )
                Icon(
                    painter = painterResource(
                        if (isNotificationActive) R.drawable.notifikasi_aktif else R.drawable.notifikasi_default
                    ),
                    contentDescription = "Notifikasi",
                    tint = colorResource(id = R.color.green_title),
                    modifier = Modifier
                        .size(26.dp)
                        .clickable { isNotificationActive = !isNotificationActive }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.live),
                    contentDescription = null,
                    modifier = Modifier.size(width = 26.dp, height = 22.dp)
                )
                Text(
                    text = "Sedang Berlangsung",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.green_title)
                )
            }
        }
        Spacer(modifier = Modifier.height(0.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(start = 15.dp, top = 0.dp, bottom = 17.dp)
            ) {
                Text(
                    text = "waktu",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.green_title),
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text(
                    text = "09.30–12.30",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.green_title)
                )
            }
            Column(
                modifier = Modifier.padding(end = 0.dp, top = 0.dp, bottom = 17.dp)
            ) {
                Text(
                    text = "Nama Mentor",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.green_title),
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text(
                    text = "Vodka",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.green_title)
                )
            }
            Button(
                onClick = { showDialog = true },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp)
                    .width(93.dp)
                    .height(26.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Gabung Live",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }

    if (showDialog) {
        ConfirmationDialog(
            message = "Apakah Kamu Mau Gabung Live?",
            onDismissRequest = { showDialog = false },
            onConfirm = {
                showDialog = false
            },
            onCancel = {
                showDialog = false
            }
        )
    }
}


@Composable
fun KategoriBertani() {
    Text(
        text = "Kategori Bertani",
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppinsFontFamily,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 37.dp, vertical = 16.dp)
    )
}

@Composable
fun SelectKategori() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 37.dp),
        horizontalArrangement = Arrangement.spacedBy(19.dp, Alignment.CenterHorizontally)
    ) {
        item {
            KategoriItem(
                iconRes = R.drawable.kacang_tanah,
                title = "Kacang Tanah"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.kacang_polong,
                title = "Kacang Polong"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.padi,
                title = "Padi"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.jagung,
                title = "Jagung"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.baru_2,
                title = "Gandum"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.kacang_tanah,
                title = "Kacang Tanah"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.kacang_polong,
                title = "Kacang Polong"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.padi,
                title = "Padi"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.jagung,
                title = "Jagung"
            )
        }
        item {
            KategoriItem(
                iconRes = R.drawable.baru_2,
                title = "Gandum"
            )
        }
    }
}

@Composable
fun KategoriItem(iconRes: Int, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 0.dp)
            .offset(y = (-20).dp)
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.size(52.dp) // Ukuran Card yang tetap
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        }

        Text(
            text = title,
            fontSize = 7.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 4.dp)
                .wrapContentWidth()
        )
    }
}

@Composable
fun RekomendasiPelatihan() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-11).dp)
            .padding(horizontal = 37.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Rekomendasi Pelatihan",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        )
        Text(
            text = "Lihat Semua",
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = poppinsFontFamily
        )
    }
}

@Composable
fun InfoCard(hai: String, title: String, deskripsi: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp), // Untuk lengkung sudut
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 37.dp, vertical = 23.dp)
                .padding(top = 5.dp)
        ) {
            // Bagian judul
            Row(
                modifier = Modifier.fillMaxWidth(), // Menjadikan Row memenuhi lebar penuh
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hai,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.background),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(3.dp)
                )

                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.background),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(3.dp)
                )

                Spacer(modifier = Modifier.weight(1f)) // Spacer untuk mendorong ikon ke kanan

                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.bookmark_putih),
                        contentDescription = "Bookmark",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }

            // Bagian deskripsi
            Text(
                text = deskripsi,
                fontSize = 13.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.background),
                modifier = Modifier.padding(3.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchBar()
        }
    }
}

@Composable
fun SearchBar() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier.size(33.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cari Pelatihan",
                color = colorResource(id = R.color.gray_text),
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun CardPelatihan(navController: NavController) {
    Box(
        modifier = Modifier
            .padding(horizontal = 37.dp),

    ) {
        Card(
            modifier = Modifier
                .size(width = 330.dp, height = 250.dp)
                .offset(y = (-11).dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp), // Mengatur bayangan
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.petani),
                        contentDescription = "Deskripsi Gambar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
//
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(24.dp)
                            .background(
                                Color.Gray,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 2.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookmark_putih),
                            contentDescription = "Bookmark",
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp) // Ukuran ikon
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "Pelatihan Menanam Kacang Tanah",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        modifier = Modifier.padding(bottom = 4.dp) // Jarak antara judul dan deskripsi
                    )

                    Text(
                        text = "Materi ini akan membahas cara menanam kacang tanah dari awal sampai akhir",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFontFamily,
                        lineHeight = 13.sp,
                        color = colorResource(id = R.color.gray_bookmark),
                        modifier = Modifier.padding(bottom = 8.dp) // Jarak antara deskripsi dan tombol
                    )

                    Button(
                        onClick = { navController.navigate(Routes.HALAMAN_SUB_MATERI) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                        modifier = Modifier
                            .width(115.dp) // Membuat tombol memenuhi lebar kolom
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
fun HalamanBerandaPreview() {
    EdufarmTheme {
        ContentScreen(
            navController = rememberNavController(),
            modifier = Modifier
        )
    }
}


