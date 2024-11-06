package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
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
                            .width(360.dp) // Mengatur lebar menjadi 360 dp
                            .height(800.dp) // Mengatur tinggi menjadi 800 dp
                            .padding(innerPadding)
                            .padding(37.dp) // Padding internal untuk konten
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
            .fillMaxWidth()
            .padding(horizontal = 37.dp, vertical = 16.dp)
    )
}

@Composable
fun SelectKategori() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 37.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        // Setiap kategori sebagai item dengan gambar dan teks
        KategoriItem(
            iconRes = R.drawable.kacang_tanah,
            title = "Kacang Tanah"
        )
        KategoriItem(
            iconRes = R.drawable.kacang_polong,
            title = "Kacang Polong"
        )
        KategoriItem(
            iconRes = R.drawable.padi,
            title = "Padi"
        )
        KategoriItem(
            iconRes = R.drawable.jagung,
            title = "Jagung"
        )
        KategoriItem(
            iconRes = R.drawable.baru_2,
            title = "Gandum"
        )
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

// Composable untuk menampilkan kartu informasi (InfoCard)
@Composable
fun InfoCard(hai: String, title: String, deskripsi: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),//untuk lengkung sudut
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green))

    ) {
        Column(modifier = Modifier
            .padding(horizontal = 37.dp, vertical = 23.dp)
            .padding(top = 5.dp)
        ) {
            // Bagian judul
            Row {
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
                Icon(
                    painter = painterResource(id =R.drawable.bookmark_putih),
                    contentDescription = null,
                    tint = colorResource(id = R.color.white),
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = 160.dp)
                )
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
        shape = RoundedCornerShape(12.dp), // Bentuk oval pada search bar
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


// Composable untuk menampilkan informasi sesi live (CardLive)
@Composable
fun CardLive() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 37.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp), // Mengatur bayangan
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_live))
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
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = colorResource(id = R.color.green_title),
                    modifier = Modifier.size(31.dp)

                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // Untuk menyelaraskan secara vertikal di tengah
                horizontalArrangement = Arrangement.spacedBy(3.dp) // Jarak antara Image dan Text
            ) {
                Image(
                    painter = painterResource(id = R.drawable.live),
                    contentDescription = null,
                    modifier = Modifier.size(width = 26.dp, height = 22.dp) // Ukuran ikon
                )

                Text(
                    text = "Sedang Berlangsung",
                    fontSize = 12.sp, // Ukuran font 12 sp
                    fontWeight = FontWeight.Medium, // Font weight medium
                    fontFamily = poppinsFontFamily, // Font Poppins
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
                    modifier = Modifier.padding(bottom = 3.dp) // Jarak antara Text atas dan bawah
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
                    modifier = Modifier.padding(bottom = 3.dp) // Jarak antara Text atas dan bawah
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
                    onClick = { /* Aksi untuk tombol Gabung Live */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp)
                        .width(93.dp) // Mengatur lebar tombol
                        .height(26.dp), // Mengatur tinggi tombol
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
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Lengkungan hanya di bagian atas gambar
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.petani), // Ganti dengan ID gambar Anda
                        contentDescription = "Deskripsi Gambar",
                        contentScale = ContentScale.Crop, // Memastikan gambar terpotong hanya pada bagian bawah
                        modifier = Modifier.fillMaxSize()
                            .padding(horizontal = 9.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(16.dp))// Agar gambar mengisi Box secara penuh
                    )
//
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd) // Menempatkan di sudut kanan atas
                            .padding(24.dp) // Memberikan jarak dari tepi
                            .background(Color.Gray, shape = RoundedCornerShape(6.dp)) // Background abu-abu dengan sudut melengkung
                            .padding(horizontal = 2.dp, vertical = 2.dp) // Padding di dalam Box untuk membuat background lebih lebar
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookmark_putih), // Ganti dengan ID ikon bookmark Anda
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
                        onClick = {},
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


@Composable
fun BottomNavigationBar() {
    NavigationBar(
        modifier = Modifier
            .height(61.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Lengkungan sudut atas kiri dan kanan
        containerColor = colorResource(id = R.color.white) // Warna latar belakang navigasi
    ) {
        Row (modifier = Modifier
            .padding(horizontal = 35.dp)
            .padding(top = 15.dp, bottom = 10.dp)
        ){
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.home), // Ganti dengan ikon rumah
                        contentDescription = "Home",
                        tint = colorResource(id = R.color.green), // Warna hijau untuk ikon aktif
                    )
                },
                label = {
                    Text(
                        text = "Beranda",
                        color = colorResource(id = R.color.green), // Warna teks aktif
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily
                    )
                },
                selected = false, // Beri true untuk menandakan ini halaman aktif
                onClick = { /* Navigasi ke halaman Beranda */ }
            )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.vidio), // Ganti dengan ikon live mentor
                    contentDescription = "Live Mentor",
                    tint = Color.Gray // Warna abu-abu untuk ikon tidak aktif
                )
            },
            label = {
                Text(
                    text = "Live Mentor",
                    color = Color.Gray, // Warna teks tidak aktif
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
                    painter = painterResource(id = R.drawable.pelatihan), // Ganti dengan ikon pelatihan
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
                    painter = painterResource(id = R.drawable.profil), // Ganti dengan ikon akun
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
