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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.ui.components.SearchBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

class HalamanSubMateri : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdufarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SubMateriScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNavigateToIsiMateri = { /* Navigasi ke halaman isi materi */ },
                        onNavigateToMateriDokumen = { /* Navigasi ke halaman dokumen */ },
                        onNavigateToMateriVideo = { /* Navigasi ke halaman video */ }
                    )
                }
            }
        }
    }
}

// Definisikan model data Materi
data class Materi(
    val title: String,
    val image: Int,
    val buttonText: String,
    val buttonAction: () -> Unit // Aksi yang akan dijalankan saat tombol diklik
)

// Lanjutkan ke dalam composable SubMateriScreen
@Composable
fun SubMateriScreen(
    modifier: Modifier = Modifier,
    onNavigateToIsiMateri: () -> Unit = {},
    onNavigateToMateriDokumen: () -> Unit = {},
    onNavigateToMateriVideo: () -> Unit = {}
) {
    // Data statis untuk listOfMateri
    val listOfMateri = listOf(
        Materi("Pemilihan Benih Kacang Tanah", R.drawable.image_1, "Ayo, Belajar", onNavigateToIsiMateri),
        Materi("Persiapan Tanah Kacang Tanah", R.drawable.image_2, "Ayo, Belajar", onNavigateToIsiMateri),
        Materi("Pengendalian Hama Kacang Tanah", R.drawable.image_3, "Ayo, Belajar", onNavigateToIsiMateri),
        Materi("Panen Tanaman Kacang Tanah", R.drawable.image_4, "Ayo, Belajar", onNavigateToIsiMateri),
        Materi("Video Tutorial Penanaman Kacang Tanah", R.drawable.image_5, "Tonton Video", onNavigateToMateriVideo),
        Materi("Dokumen Tentang Kacang Tanah", R.drawable.image_6, "Download", onNavigateToMateriDokumen)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(start = 35.dp, end = 35.dp, top = 5.dp)
    ) {
        // Top bar
        TopBar(title = "Materi")
        Spacer(modifier = Modifier.height(8.dp))

        // Search bar
        SearchBar(placeholder = "Cari Pelatihan")
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ayo Kita Mulai Belajar",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 23.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // List of cards for each materi
        LazyColumn {
            items(listOfMateri) { materi ->
                MateriCard(materi = materi)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MateriCard(materi: Materi) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(151.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 18.dp, top = 24.dp, bottom = 24.dp, end = 18.dp)
                .fillMaxWidth()
                .height(104.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = materi.image),
                contentDescription = "Image for ${materi.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 120.dp, height = 104.dp)
                    .clip(RoundedCornerShape(14.dp))
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = materi.title,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)

                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = materi.buttonAction,
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.green)
                    ),
                    contentPadding = PaddingValues(horizontal = 3.dp, vertical = 0.dp),
                    modifier = Modifier
                        .width(78.dp)
                        .height(22.dp)
                ) {
                    Text(
                        text = materi.buttonText,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily,
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSubMateriScreen() {
    EdufarmTheme {
        SubMateriScreen(
            modifier = Modifier,
            onNavigateToIsiMateri = {},
            onNavigateToMateriDokumen = {},
            onNavigateToMateriVideo = {}
        )
    }
}
