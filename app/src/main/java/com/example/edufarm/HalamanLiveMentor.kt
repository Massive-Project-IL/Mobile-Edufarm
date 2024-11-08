package com.example.edufarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.CardLive
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily


@Composable
fun LiveMentorScreen(
    navController: NavController, modifier: Modifier = Modifier
) {
    val selectedItem = remember { mutableStateOf("Live Mentor") } // Menyimpan item yang dipilih

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                modifier = Modifier.fillMaxWidth(),
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
}

@Composable
fun LiveMentorDescription() {
    Column(
        modifier = Modifier.fillMaxWidth()
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
        LiveMentorScreen(
            navController = rememberNavController(), modifier = Modifier
        )
    }
}