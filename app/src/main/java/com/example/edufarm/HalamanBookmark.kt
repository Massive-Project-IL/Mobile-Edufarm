package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.ui.components.CardPelatihan
import com.example.edufarm.ui.components.SearchBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily


class HalamanBookmarkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdufarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookmarkScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun BookmarkScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(start = 35.dp, end = 35.dp, top = 5.dp)
    ) {
        TopBar(title = "Simpan Pelatihan")
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(placeholder = "Cari Pelatihan")
        Spacer(modifier = Modifier.height(16.dp))

        // Teks "Kategori"
        Text(
            text = "Kategori",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily,
            letterSpacing = (-0.24).sp,
            lineHeight = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        CategoryChips()
        Spacer(modifier = Modifier.height(16.dp))

        // Memanggil CardPelatihan dengan konten bookmark
        CardPelatihan(
            title = "Pelatihan Menanam Kacang Tanah",
            description = "Materi ini akan membahas cara menanam kacang tanah dari awal sampai akhir.",
            imageRes = R.drawable.petani,
            onBookmarkClick = { /* Aksi bookmark */ },
            onButtonClick = { /* Aksi lihat selengkapnya */ },
            poppinsFontFamily = poppinsFontFamily // Pastikan font tersedia di proyek
        )
    }
}


@Composable
fun CategoryChips() {
    val categories = listOf("Kacang Tanah", "Kacang Polong", "Jagung", "Gandum", "Kedelai")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(14.dp) // Jarak antar elemen 14dp
    ) {
        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.white),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable { /* Aksi pilih kategori */ }
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.green),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = category,
                    color = colorResource(R.color.gray_bookmark),
                    fontSize = 10.sp, // Ukuran font sesuai gambar
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    lineHeight = 20.sp, // Line height sesuai gambar
                    letterSpacing = (-0.24).sp // Letter spacing sesuai gambar
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewBookmarkScreen() {
    EdufarmTheme {
        BookmarkScreen(modifier = Modifier)
    }
}