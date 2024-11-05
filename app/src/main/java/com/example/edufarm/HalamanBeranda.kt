package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

class HalamanBeranda : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdufarmTheme {
                Scaffold { innerPadding ->
                    ContentScreen(
                        modifier = Modifier
                            .width(360.dp)
                            .height(800.dp)
                            .padding(innerPadding)
                            .padding(37.dp)
                    )
                }
            }
        }
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
            .padding(top = 16.dp)
            .padding(horizontal = 37.dp)
    )
}

@Composable
fun SelectKategori() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        KategoriItem(iconRes = R.drawable.kacang_tanah, title = "Kacang Tanah")
        KategoriItem(iconRes = R.drawable.kacang_polong, title = "Kacang Polong")
        KategoriItem(iconRes = R.drawable.padi, title = "Padi")
        KategoriItem(iconRes = R.drawable.jagung, title = "Jagung")
        KategoriItem(iconRes = R.drawable.baru_2, title = "Gandum")
    }
}

@Composable
fun KategoriItem(iconRes: Int, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding()
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.size(52.dp)
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
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Composable
fun RekomendasiPelatihan() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
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
            .height(132.dp),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green))
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 37.dp)
            ) {
                Row {
                    Text(
                        text = hai,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.kategori_white),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(3.dp)
                    )
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.kategori_white),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(3.dp)
                    )
                }
                Text(
                    text = deskripsi,
                    fontSize = 13.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.kategori_white)
                )
                Spacer(modifier = Modifier.height(13.dp))
                SearchBar()
            }
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
                .padding(start = 16.dp, top = 5.dp),
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
fun CardLive() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 34.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_live))
    ) {
        Column(modifier = Modifier.padding(top = 11.dp, bottom = 10.dp, start = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Bertanam Gandum",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.green_titel)
                )
                Icon(
                    painter = painterResource(id = R.drawable.lonceng),
                    contentDescription = null,
                    tint = colorResource(id = R.color.green_titel),
                    modifier = Modifier
                        .padding(end = 18.dp, top = 13.dp)
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
                    color = colorResource(id = R.color.green_titel)
                )
            }
        }
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
                    color = colorResource(id = R.color.green_titel),
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text(
                    text = "09.30–12.30",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.green_titel)
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
                    color = colorResource(id = R.color.green_titel),
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text(
                    text = "Vodka",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.green_titel)
                )
            }
            Button(
                onClick = { /* Aksi untuk tombol Gabung Live */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                modifier = Modifier
                    .padding( end = 7.dp, top = 5.dp)
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
}

@Composable
fun CardPelatihan() {
    Box(
        modifier = Modifier.padding(horizontal = 37.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.petani),
                        contentDescription = "Deskripsi Gambar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 9.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(24.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(6.dp))
                            .padding(horizontal = 2.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookmark_putih),
                            contentDescription = "Bookmark",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
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
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Materi ini akan membahas cara menanam kacang tanah dari awal sampai akhir",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFontFamily,
                        lineHeight = 13.sp,
                        color = colorResource(id = R.color.gray_bookmark),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Button(
                        onClick = {},
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

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        modifier = Modifier
            .height(61.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = colorResource(id = R.color.white)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(top = 15.dp, bottom = 10.dp)
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Home",
                        tint = colorResource(id = R.color.green),
                    )
                },
                label = {
                    Text(
                        text = "Beranda",
                        color = colorResource(id = R.color.green),
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily
                    )
                },
                selected = false,
                onClick = { /* Navigasi ke halaman Beranda */ }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.vidio),
                        contentDescription = "Live Mentor",
                        tint = Color.Gray
                    )
                },
                label = {
                    Text(
                        text = "Live Mentor",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        lineHeight = 10.sp
                    )
                },
                selected = false,
                onClick = { /* Navigasi ke halaman Live Mentor */ }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.pelatihan),
                        contentDescription = "Pelatihan",
                        tint = Color.Gray
                    )
                },
                label = {
                    Text(
                        text = "Pelatihan",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        lineHeight = 10.sp
                    )
                },
                selected = false,
                onClick = { /* Navigasi ke halaman Pelatihan */ }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.profil),
                        contentDescription = "Akun",
                        tint = Color.Gray
                    )
                },
                label = {
                    Text(
                        text = "Akun",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        lineHeight = 10.sp
                    )
                },
                selected = false,
                onClick = { /* Navigasi ke halaman Akun */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HalamanBerandaPreview() {
    EdufarmTheme {
        Scaffold {
            ContentScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier) {
    Column {
        InfoCard(
            hai = "Hai,",
            title = "Petani👋",
            deskripsi = "Ayo kita belajar bertani bersama!"
        )
        Column {
            Spacer(modifier = Modifier.height(16.dp))
        }
        CardLive()
        KategoriBertani()
        Spacer(modifier = Modifier.height(10.dp))
        SelectKategori()
        RekomendasiPelatihan()
        Spacer(modifier = Modifier.height(10.dp))
        CardPelatihan()
        BottomNavigationBar()
    }
}
