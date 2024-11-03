package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                Scaffold(
                    bottomBar = { BottomNavigationBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ContentScreen(
                        modifier = Modifier
                            .width(360.dp) // Mengatur lebar menjadi 360 dp
                            .height(800.dp) // Mengatur tinggi menjadi 800 dp
                            .padding(innerPadding)
                            .padding(16.dp) // Padding internal untuk konten
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
        horizontalArrangement = Arrangement.spacedBy(14.dp)
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




@Composable
fun ContentScreen(modifier: Modifier = Modifier) {
    Column {
        InfoCard(
            title = "Hai, Petani",
            deskripsi = "Ayo kita belajar bertani bersama!"
        )
        Column {
            Spacer(modifier = Modifier.height(16.dp))
        }
        // Menampilkan CardLive di bawah InfoCard
        CardLive()

        KategoriBertani()
            Spacer(modifier = Modifier.height(10.dp))
        SelectKategori()
        RekomendasiPelatihan()
    }
    }



// Composable untuk menampilkan kartu informasi (InfoCard)
@Composable
fun InfoCard(title: String, deskripsi: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp)
            .padding(0.dp),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),//untuk lengkung sudut
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green))

    ) {
        Column(modifier = Modifier.padding(30.dp)) {
            // Bagian judul
            Row {
                Text(
                    text = title,
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(3.dp)
                )
            }

            // Bagian deskripsi
            Text(
                text = deskripsi,
                fontSize = 15.sp,
                color = colorResource(id = R.color.white),
                modifier = Modifier.offset(x = 5.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Kolom pencarian di bagian bawah
            SearchBar()
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
                    color = colorResource(id = R.color.green_titel)
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = colorResource(id = R.color.green_titel)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.live),
                    contentDescription = null,
                    modifier = Modifier.size(width = 22.dp, height = 22.dp) // Ukuran ikon
                )

                Text(
                    text = "Sedang Berlangsung",
                    fontSize = 12.sp, // Mengatur ukuran font menjadi 11 sp
                    fontWeight = FontWeight.Medium, // Menggunakan font weight medium
                    fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                    color = colorResource(id = R.color.green_titel),
                    modifier = Modifier.offset(x = (3).dp) // Geser ke kiri sebesar 4 dp
                )
            }
        }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "waktu",
                        fontSize = 12.sp, // Mengatur ukuran font menjadi 11 sp
                        fontWeight = FontWeight.Medium, // Menggunakan font weight medium
                        fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                        color = colorResource(id = R.color.green_titel),
                        modifier = Modifier.offset(x = (15).dp, y = (-12).dp)
                    )
                    Text(
                        text = "09.30–12.30",
                        fontSize = 12.sp, // Mengatur ukuran font menjadi 11 sp
                        fontWeight = FontWeight.Medium, // Menggunakan font weight medium
                        fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                        color = colorResource(id = R.color.green_titel),
                        modifier = Modifier.offset(x = (15).dp, y = (-15).dp)
                    )
                }
                Column {
                    Text(
                        text = "Nama Mentor",
                        fontSize = 12.sp, // Mengatur ukuran font menjadi 11 sp
                        fontWeight = FontWeight.Medium, // Menggunakan font weight medium
                        fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                        color = colorResource(id = R.color.green_titel),
                        modifier = Modifier.offset(x = (15).dp, y = (-12).dp)
                    )
                    Text(
                        text = "Vodka",
                        fontSize = 12.sp, // Mengatur ukuran font menjadi 11 sp
                        fontWeight = FontWeight.Medium, // Menggunakan font weight medium
                        fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                        color = colorResource(id = R.color.green_titel),
                        modifier = Modifier.offset(x = (15).dp, y = (-15).dp)
                    )
                }
                Button(
                    onClick = { /* Aksi untuk tombol Gabung Live */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                    modifier = Modifier
                        .offset(x = (-15).dp, y = (-6).dp) // Menggeser tombol ke kiri dan ke atas
                        .width(91.dp) // Mengatur lebar tombol
                        .height(24.dp), // Mengatur tinggi tombol
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



// Composable untuk membuat Bottom Navigation Bar
@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text("Home", color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp)
        }
    }
}

// Preview untuk menampilkan tampilan halaman beranda
@Preview(showBackground = true)
@Composable
fun HalamanBerandaPreview() {
    EdufarmTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar() }
        ) {
            ContentScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
            )
        }
    }
}
