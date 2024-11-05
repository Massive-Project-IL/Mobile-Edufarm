package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

class HalamanBerandaTanpaJadwal : ComponentActivity() {
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
fun CardLiveTanpaJadwal() {
    Box(modifier = Modifier.padding(horizontal = 37.dp)) {
        Card(
            modifier = Modifier
                .width(319.dp)
                .height(120.dp),
        ) {
            Box(modifier = Modifier
                .padding(top = 17.dp, bottom = 33.dp)
                .padding(horizontal = 36.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.jadwal_kosong__1_),
                    contentDescription = "jadwal Kosong",
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
        Row (modifier = Modifier
            .align(Alignment.Center)){
            Text(
                text = "Yahh, Hari ini engga ada live mentor",
                fontSize = 12.sp, // Gunakan sp untuk ukuran font
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily,
                color = colorResource(id = R.color.green_titel),
                modifier = Modifier.padding(top = 70.dp), // Jarak antara gambar dan tek
            )
        }
    }
}
@Composable
fun BottomNavigationBarTanpaJadwal() {
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
@Composable
fun JadwalContentScreen(modifier: Modifier = Modifier) {
    Column {
        InfoCard(
            hai = "Hai,",
            title = "Petani👋",
            deskripsi = "Ayo kita belajar bertani bersama!"
        )
        Spacer(modifier = Modifier.height(16.dp))
        CardLiveTanpaJadwal()
        KategoriBertani()
        Spacer(modifier = Modifier.height(10.dp))
        SelectKategori()
        RekomendasiPelatihan()
        Spacer(modifier = Modifier.height(10.dp))
        CardPelatihan()
        BottomNavigationBarTanpaJadwal()
    }
}

@Preview(showBackground = true)
@Composable
fun JadwalContentScreenPreview() {
    EdufarmTheme {
        JadwalContentScreen() // Pastikan ini memanggil fungsi konten utama dan bukan dirinya sendiri
    }
}
