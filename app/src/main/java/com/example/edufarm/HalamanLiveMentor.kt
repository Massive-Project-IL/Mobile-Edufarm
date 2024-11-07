package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

class HalamanLiveMentor : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdufarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LiveMentorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LiveMentorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(start = 35.dp, end = 35.dp, top = 5.dp)
    ) {
        // Header Section
        Text(
            text = "Live Mentor",
            fontSize = 18.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Jadwal Live Hari ini",
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                lineHeight = 23.sp,
                letterSpacing = 0.02.em, // Mengatur letter spacing menjadi 2%
                color = Color.Black
            )
            Button(
                onClick = { /* Handle Lihat Jadwal click */ },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.green)
                ),
                contentPadding = PaddingValues(horizontal = 1.dp, vertical = 0.dp),
                modifier = Modifier
                    .width(80.dp)
                    .height(25.dp)
            ) {
                Text(
                    text = "Lihat Jadwal",
                    color = Color.White,
                    fontSize = 8.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.02.em, // Mengatur letter spacing menjadi 2%
                    )
            }
        }
        // Placeholder for content below the header
        // Tambahkan konten lain di sini sesuai kebutuhan
        Spacer(modifier = Modifier.height(16.dp))
        CardLive()
        Spacer(modifier = Modifier.height(16.dp))
        LiveMentorDescription()

    }
}

@Composable
fun CardLive() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 37.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp), // Mengatur bayangan
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_logo))
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
                    color = colorResource(id = R.color.green)
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = colorResource(id = R.color.green)
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
                    color = colorResource(id = R.color.green),
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
                    color = colorResource(id = R.color.green),
                    modifier = Modifier.offset(x = (15).dp, y = (-12).dp)
                )
                Text(
                    text = "09.30–12.30",
                    fontSize = 12.sp, // Mengatur ukuran font menjadi 11 sp
                    fontWeight = FontWeight.Medium, // Menggunakan font weight medium
                    fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                    color = colorResource(id = R.color.green),
                    modifier = Modifier.offset(x = (15).dp, y = (-15).dp)
                )
            }
            Column {
                Text(
                    text = "Nama Mentor",
                    fontSize = 12.sp, // Mengatur ukuran font menjadi 11 sp
                    fontWeight = FontWeight.Medium, // Menggunakan font weight medium
                    fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                    color = colorResource(id = R.color.green),
                    modifier = Modifier.offset(x = (15).dp, y = (-12).dp)
                )
                Text(
                    text = "Vodka",
                    fontSize = 12.sp, // Mengatur ukuran font menjadi 11 sp
                    fontWeight = FontWeight.Medium, // Menggunakan font weight medium
                    fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                    color = colorResource(id = R.color.green),
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

@Composable
fun LiveMentorDescription() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Title
        Text(
            text = "Yuk, Bertani Gandum Bareng! 🎉",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Subheading
        Text(
            text = "Live Mentor: Bertanam Gandum 🌾",
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text = "Belajar menanam gandum dari ahlinya! 🌱 Simak tips dan trik bertanam gandum yang benar, mulai dari pemilihan benih, pengolahan tanah, hingga perawatannya.",
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Section Title
        Text(
            text = "Yang akan kamu dapatkan:",
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // List of Benefits
        val benefits = listOf(
            "Panduan praktis: Belajar langsung dari pakar pertanian berpengalaman.",
            "Sharing pengalaman: Berdiskusi dan berbagi pengalaman dengan petani lainnya.",
            "Tanya jawab: Ajukan pertanyaan tentang menanam gandum sepuasnya.",
            "Motivasi dan inspirasi: Dapatkan semangat dan inspirasi untuk memulai bertani."
        )

        benefits.forEach { benefit ->
            Text(
                text = "- $benefit",
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Closing Text
        Text(
            text = "Gabung sekarang dan raih hasil panen yang maksimal! Jangan lewatkan kesempatan emas ini! 🎉",
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewLiveMentorScreen() {
    EdufarmTheme {
        LiveMentorScreen(modifier = Modifier)
    }
}