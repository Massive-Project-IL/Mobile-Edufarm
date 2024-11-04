package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.edufarm.ui.components.SearchBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme

class HalamanSubMateri : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdufarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SubMateriScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Definisikan model data Materi
data class Materi(val title: String, val image: String)

// Lanjutkan ke dalam composable SubMateriScreen
@Composable
fun SubMateriScreen(modifier: Modifier = Modifier) {
    // Data statis untuk listOfMateri
    val listOfMateri = listOf(
        Materi("Pemilihan Benih Kacang Tanah", "https://example.com/image1.jpg"),
        Materi("Persiapan Tanah Kacang Tanah", "https://example.com/image2.jpg"),
        Materi("Pengendalian Hama Kacang Tanah", "https://example.com/image3.jpg"),
        Materi("Panen Tanaman Kacang Tanah", "https://example.com/image4.jpg")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top bar
        TopBar(title = "Materi")
        Spacer(modifier = Modifier.height(8.dp))

        // Search bar
        SearchBar(placeholder = "Cari Pelatihan")
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ayo Kita Mulai Belajar",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // List of cards for each materi
        LazyColumn {
            items(listOfMateri) { materi ->
                MateriCard(materi = materi)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun MateriCard(materi: Materi) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // Memusatkan elemen secara vertikal
        ) {
            Image(
                painter = painterResource(id = R.drawable.petani), // Ganti dengan gambar lokal
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = materi.title,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { /* Handle click action */ },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Ayo, Belajar")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSubMateriScreen() {
    EdufarmTheme {
        SubMateriScreen(modifier = Modifier)
    }
}