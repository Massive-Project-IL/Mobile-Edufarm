package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily


class HalamanMateriDokumen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdufarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   MateriDokumenScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun MateriDokumenScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(start = 35.dp, end = 35.dp, top = 5.dp)
    ) {
        TopBar(title = "Materi")
        Spacer(modifier = Modifier.height(25.dp))

        // Judul Materi Dokumen
        Text(
            text = "Dokumen Tambahan: Penanaman Kacang Tanah",
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily,
            color = colorResource(R.color.black)
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Deskripsi Dokumen
        Text(
            text = "Menurut jurnal pertanian yang diterbitkan oleh International Journal of Agronomy, persiapan lahan yang baik dapat secara signifikan meningkatkan hasil kacang tanah. Mereka menemukan bahwa tanah yang digemburkan hingga kedalaman 25-30 cm memungkinkan akar tanaman untuk tumbuh lebih dalam, serta meningkatkan retensi air dan drainase. Penanaman kacang tanah juga memerlukan rotasi tanaman dengan tanaman lain seperti jagung atau kedelai untuk menjaga kesuburan tanah dan menghindari kelelahan tanah akibat penanaman monokultur.\n" +
                    "\n" +
                    "Sebuah artikel dari Journal of Crop Science menekankan pentingnya memilih waktu tanam yang tepat, yaitu pada musim penghujan, ketika kelembapan tanah cukup tinggi untuk mendukung pertumbuhan benih. Pemberian pupuk organik juga direkomendasikan karena dapat meningkatkan struktur tanah dan menyediakan nutrisi yang lebih seimbang bagi tanaman kacang tanah dibandingkan pupuk kimia.\n" +
                    "\n" +
                    "Nahh, Untuk Selengkapnya mengenai jurnal, kalian bisa download file nya di bawah ini yaa!",
            fontSize = 10.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp,
            fontWeight = FontWeight.Normal,
            fontFamily = poppinsFontFamily,
            color = colorResource(R.color.black)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Download Dokumen
        Button(
            onClick = { /* Tambahkan aksi download di sini */ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Text(
                text = "Download dokumen",
                color = colorResource(R.color.white),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewMateriDokumenScreen() {
    EdufarmTheme {
        MateriDokumenScreen(modifier = Modifier)
    }
}