package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

class HalamanIsiMateri : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdufarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IsiMateriScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun IsiMateriScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
    ) {
        // Top Bar dengan Padding
        TopBar(
            title = "Materi",
            modifier = Modifier
                .padding(start = 35.dp, end = 35.dp, top = 5.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Gambar Utama yang memenuhi lebar layar
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.petani), // Ganti dengan ID gambar yang sesuai
                contentDescription = "Gambar Materi",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Konten Utama di Kolom dengan Padding
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp)
        ) {
            // Judul Materi
            Text(
                text = "Pemilihan Benih Kacang Tanah",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,
                color = colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Konten Materi
            Text(
                text = "Menanam kacang tanah yang sehat dimulai dari pemilihan benih berkualitas. Benih yang unggul memiliki ciri berisi penuh, tidak keriput, dan bebas dari penyakit. Proses pemilihan ini penting untuk memastikan hasil panen yang optimal. Tanah yang subur, terhidrasi dengan baik, serta jarak tanam yang tepat akan meningkatkan kemampuan tanaman kacang tanah untuk berkembang secara efisien. Pastikan memilih benih yang sesuai dengan kondisi tanah di wilayah Anda agar hasil panen maksimal.\n" +
                        "\n" +
                        "Proses pemilihan benih kacang tanah yang tepat sangat krusial dalam menentukan keberhasilan pertumbuhan tanaman. Benih yang sehat harus dipilih dari tanaman yang tidak terserang hama atau penyakit, serta memiliki ukuran yang seragam dan berwarna cerah. Sebaiknya, hindari penggunaan benih yang terlalu kecil atau rusak, karena hal ini dapat berdampak pada rendahnya produktivitas. Menurut para ahli agronomi, menggunakan benih dengan daya kecambah yang baik bisa meningkatkan hasil panen hingga 20% lebih tinggi dibandingkan benih yang asal-asalan..",
                fontSize = 10.sp,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp,
                fontWeight = FontWeight.Normal,
                fontFamily = poppinsFontFamily,
                color = colorResource(R.color.black)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewIsiMateriScreen() {
    EdufarmTheme {
        IsiMateriScreen(modifier = Modifier)
    }
}