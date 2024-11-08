package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

class HalamanPelatihan : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdufarmTheme {
                PelatihanScreen()
            }
        }
    }
}

@Composable
fun PelatihanScreen() {
    Box (modifier = Modifier
        .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
        ) {
            // Judul
            Text(
                text = "Pelatihan",
                fontSize = 18.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
            // Search Bar
            SearchBarPelatihan()
            Text(
                text = "Kategori",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            // Kategori
            Spacer(modifier = Modifier.height(12.dp))
            KategoriChips()

            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(5) {
                    CardPelatihanKategori()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        BottomNavigationBarPelatihan(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun SearchBarPelatihan() {
    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(40.dp)
            .border(1.dp, colorResource(id = R.color.green_button), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding()
                .padding(start = 17.dp, bottom = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier
                    .size( width = 25.dp, height = 25.dp),
                tint = colorResource(R.color.gray_live)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cari Pelatihan",
                color = Color.Gray,
                fontSize = 12 .sp
            )
        }
    }
}


@Composable
fun KategoriChips() {
    val categories = listOf("Kacang Tanah", "Kacang Polong", "Jagung", "Gandum", "Kedelai")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .background(
                        color = if (category == selectedCategory) colorResource(R.color.green)
                        else colorResource(R.color.white),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable { selectedCategory = category }
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.green),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = category,
                    color = if (category == selectedCategory) colorResource(R.color.white)
                    else colorResource(R.color.gray_bookmark),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    lineHeight = 20.sp,
                    letterSpacing = (-0.24).sp
                )
            }
        }
    }
}

@Composable
fun CardPelatihanKategori(){
    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(260.dp)
            .offset(y = (-11).dp)
            .shadow(
                elevation = 15.dp, // Ketinggian bayangan
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                ambientColor = Color.Black.copy(alpha = 3.0f), // Warna bayangan
                spotColor = Color.Black.copy(alpha = 3.0f),
                clip = false
            ),
        shape = RoundedCornerShape(16.dp),
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
                            modifier = Modifier
                                .size(24.dp)
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

@Composable
fun BottomNavigationBarPelatihan(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        NavigationBar(
            modifier = Modifier
                .height(61.dp)
                .fillMaxWidth()
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
                    onClick = { /* halaman Beranda */ }
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
}

@Preview(showBackground = true )
@Composable
fun PreviewPelatihanScreen() {
    EdufarmTheme {
        Column {
            PelatihanScreen()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
