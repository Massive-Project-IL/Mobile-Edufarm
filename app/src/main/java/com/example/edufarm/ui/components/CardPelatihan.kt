package com.example.edufarm.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.R

@Composable
fun CardPelatihan(
    title: String,
    description: String,
    imageRes: Int,
    onBookmarkClick: () -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    poppinsFontFamily: FontFamily // Pastikan poppinsFontFamily tersedia di proyek Anda
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 9.dp, end = 9.dp) // Padding antara gambar dan tepi kartu
            ) {
                // Gambar Utama
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(122.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                // Ikon Bookmark
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 19.dp, end = 15.dp)
                        .size(24.dp) // Ukuran total termasuk ikon dan padding
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bookmark_green),
                        contentDescription = "Bookmark",
                        modifier = Modifier.size(width = 16.dp, height = 18.dp) // Ukuran ikon
                    )
                }
            }

            // Menambahkan jarak antara gambar dan konten teks
            Spacer(modifier = Modifier.height(16.dp))

            // Konten Teks
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily, // Menggunakan font Poppins
                    color = colorResource(R.color.gray_bookmark)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green)),
                shape = RoundedCornerShape(6.dp), // Menyesuaikan sudut sesuai desain
                modifier = Modifier
                    .height(40.dp) // Menyesuaikan tinggi tombol
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Lihat Selengkapnya",
                    color = Color.White,
                    fontSize = 14.sp, // Menyesuaikan ukuran teks
                    fontWeight = FontWeight.Medium, // Menyesuaikan ketebalan teks
                    fontFamily = poppinsFontFamily // Menggunakan font Poppins
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

