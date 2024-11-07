package com.example.edufarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun IsiMateriScreen(id: Int?, title: String?, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
    ) {
        // Top Bar dengan Padding
        TopBar(
            title = "Materi",
            navController = navController,
            modifier = Modifier
                .padding(start = 35.dp, end = 35.dp, top = 5.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Gambar Utama yang memenuhi lebar layar, berada di luar area scroll
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.petani),
                contentDescription = "Gambar Materi",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Konten yang dapat di-scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Menambahkan scroll pada kolom konten
                .padding(horizontal = 35.dp)
        ) {
            // Judul Materi dan ID
            Text(
                text = "$title",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,
                color = colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Konten Materi yang panjang
            Text(
                text = """
                    Menanam kacang tanah yang sehat dimulai dari pemilihan benih berkualitas. Benih yang unggul memiliki ciri berisi penuh, tidak keriput, dan bebas dari penyakit. Proses pemilihan ini penting untuk memastikan hasil panen yang optimal. Tanah yang subur, terhidrasi dengan baik, serta jarak tanam yang tepat akan meningkatkan kemampuan tanaman kacang tanah untuk berkembang secara efisien. Pastikan memilih benih yang sesuai dengan kondisi tanah di wilayah Anda agar hasil panen maksimal.

                    Proses pemilihan benih kacang tanah yang tepat sangat krusial dalam menentukan keberhasilan pertumbuhan tanaman. Benih yang sehat harus dipilih dari tanaman yang tidak terserang hama atau penyakit, serta memiliki ukuran yang seragam dan berwarna cerah. Sebaiknya, hindari penggunaan benih yang terlalu kecil atau rusak, karena hal ini dapat berdampak pada rendahnya produktivitas. Menurut para ahli agronomi, menggunakan benih dengan daya kecambah yang baik bisa meningkatkan hasil panen hingga 20% lebih tinggi dibandingkan benih yang asal-asalan.

                    Tanah yang ideal untuk kacang tanah adalah yang gembur dan kaya akan nutrisi. Persiapan tanah yang baik meliputi penggemburan, pemberian pupuk organik, dan pengendalian gulma sebelum tanam. Jika tanah memiliki tekstur yang terlalu keras, akar tanaman akan kesulitan menyerap nutrisi, yang pada akhirnya menghambat pertumbuhan kacang tanah. 

                    Penerapan teknik pemupukan yang tepat akan mendukung pertumbuhan tanaman kacang tanah secara optimal. Disarankan untuk memberikan pupuk kandang atau kompos yang telah matang, karena jenis pupuk ini dapat meningkatkan kualitas tanah secara alami. Selain itu, pengaturan irigasi yang baik sangat penting dalam budidaya kacang tanah, terutama pada fase awal pertumbuhan dimana tanaman membutuhkan kelembaban yang stabil. 

                    Pengendalian hama dan penyakit juga harus dilakukan secara teratur. Beberapa hama utama yang sering menyerang tanaman kacang tanah antara lain kutu daun, ulat grayak, dan lalat kacang tanah. Penggunaan pestisida nabati, seperti ekstrak daun mimba atau bawang putih, dapat membantu mengurangi serangan hama tanpa merusak keseimbangan ekosistem. 

                    Dengan perawatan yang tepat mulai dari pemilihan benih hingga pengendalian hama, petani dapat memastikan bahwa tanaman kacang tanah tumbuh sehat dan menghasilkan panen yang berkualitas tinggi. Proses ini membutuhkan ketelitian dan ketekunan, namun hasilnya akan sepadan dengan usaha yang dikerahkan.
                """.trimIndent(),
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
        IsiMateriScreen(
            navController = rememberNavController(),
            id = 1,  // Pastikan id adalah Int
            title = "Preview Title"  // Berikan nilai contoh untuk title
        )
    }
}

