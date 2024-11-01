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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
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
fun BookmarkScreen(modifier: Modifier = Modifier) {
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
        BookmarkCard(
            title = "Pelatihan Menanam Kacang Tanah",
            description = "Materi ini akan membahas cara menanam kacang tanah dari awal sampai akhir.",
            imageRes = R.drawable.petani,
            onBookmarkClick = { /* Aksi saat bookmark diklik */ },
            onButtonClick = { /* Aksi saat tombol diklik */ }
        )

    }
}


@Composable
fun TopBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { /* Aksi kembali */ },
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(21.dp)
                    .align(Alignment.CenterStart)
            )
        }
        Spacer(modifier = Modifier.width(45.dp))
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
            .height(45.dp)
            .background(
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 2.dp,
                color = colorResource(R.color.green),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search Icon",
                tint = colorResource(R.color.gray_live),
                modifier = Modifier.size(width = 25.5.dp, height = 24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
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
                    innerTextField()
                }
            )
        }
    }
}


@Composable
fun CategoryChips() {
    val categories = listOf("Kacang Tanah", "Kacang Polong", "Jagung", "Gandum")
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
                        width = 2.dp,
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


@Composable
fun BookmarkCard(
    title: String,
    description: String,
    imageRes: Int,
    onBookmarkClick: () -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
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
            Column(modifier = Modifier
                .padding(horizontal = 16.dp)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
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
                    fontFamily = poppinsFontFamily
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
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