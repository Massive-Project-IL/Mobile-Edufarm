package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun BookmarkScreen(modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(16.dp)
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))
        CategoryChips()
        Spacer(modifier = Modifier.height(16.dp))
        BookmarkCard()
    }
}

@Composable
fun TopBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { /* Aksi kembali */ },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier.size(21.dp)
            )
        }
        Text(
            text = "Simpan Pelatihan",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        )
    }
}


@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("Cari Pelatihan")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = colorResource(R.color.white), RoundedCornerShape(10.dp))
            .border(
                width = 2.dp,
                color = colorResource(R.color.green), // Warna hijau untuk border
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Ikon Pencarian
            Icon(
                painter = painterResource(id = R.drawable.search), // Ganti dengan ikon pencarian Anda
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(width = 25.5.dp, height = 24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Text Field
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray_text)
                ),
                decorationBox = { innerTextField ->
                    if (searchQuery.text.isEmpty()) {
                        Text(
                            text = "Cari Pelatihan",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 16.sp,
                                color = colorResource(R.color.gray_text),
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                    innerTextField() // Menampilkan isi dari BasicTextField
                }
            )
        }
    }
}


@Composable
fun CategoryChips() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        val categories = listOf("Kacang Tanah", "Kacang Polong", "Jagung", "Gandum")
        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .background(color = colorResource(R.color.white), RoundedCornerShape(6.dp))
                    .clickable { /* Aksi pilih kategori */ }
                    .border(
                        width = 2.dp,
                        color = colorResource(R.color.green), // Warna hijau untuk border
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = category,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}

@Composable
fun BookmarkCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Gambar placeholder, ganti dengan gambar dari resources
            Image(
                painter = painterResource(id = R.drawable.petani), // Sesuaikan dengan resource gambar
                contentDescription = "Gambar Bookmark",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pelatihan Menanam Kacang Tanah",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Materi ini akan membahas cara menanam kacang tanah dari awal sampai akhir",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Aksi lihat selengkapnya */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Lihat Selengkapnya", color = Color.White)
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