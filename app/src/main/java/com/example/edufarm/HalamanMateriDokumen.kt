package com.example.edufarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MateriDokumenScreen(id: Int?, title: String?, navController: NavController) {
    val listState = rememberLazyListState()
    val isAtBottom = remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }
    var showDialog by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = false
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(start = 35.dp, end = 35.dp, top = 5.dp)
    ) {
        TopBar(
            navController = navController,
            title = "Materi"
        )
        Spacer(modifier = Modifier.height(20.dp))


        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
        ) {
            item {
                Text(
                    text = "$title",
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    letterSpacing = (-0.24).sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(R.color.black)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Text(
                    text = """
                        Menurut jurnal pertanian yang diterbitkan oleh International Journal of Agronomy, persiapan lahan yang baik dapat secara signifikan meningkatkan hasil kacang tanah. Mereka menemukan bahwa tanah yang digemburkan hingga kedalaman 25-30 cm memungkinkan akar tanaman untuk tumbuh lebih dalam, serta meningkatkan retensi air dan drainase. Penanaman kacang tanah juga memerlukan rotasi tanaman dengan tanaman lain seperti jagung atau kedelai untuk menjaga kesuburan tanah dan menghindari kelelahan tanah akibat penanaman monokultur.

                        Sebuah artikel dari Journal of Crop Science menekankan pentingnya memilih waktu tanam yang tepat, yaitu pada musim penghujan, ketika kelembapan tanah cukup tinggi untuk mendukung pertumbuhan benih. Pemberian pupuk organik juga direkomendasikan karena dapat meningkatkan struktur tanah dan menyediakan nutrisi yang lebih seimbang bagi tanaman kacang tanah dibandingkan pupuk kimia.

                        Selain itu, penelitian dari Journal of Soil and Water Conservation menunjukkan bahwa teknik pengairan yang baik sangat penting untuk tanaman kacang tanah. Pengairan yang tidak memadai atau berlebihan dapat merusak kualitas tanaman dan mengurangi hasil panen. Oleh karena itu, menjaga keseimbangan air dalam tanah adalah faktor kunci dalam budidaya kacang tanah yang sukses.

                        Menurut pakar agronomi, menjaga keseimbangan nutrisi tanah adalah langkah penting untuk menjamin keberhasilan panen. Mereka merekomendasikan penggunaan pupuk organik seperti kompos dan pupuk kandang yang telah difermentasi dengan baik, karena ini akan memperbaiki struktur tanah secara alami tanpa merusak mikroorganisme tanah.

                        Sementara itu, sebuah penelitian dari Agricultural Research Journal mengungkapkan bahwa penggunaan pestisida alami, seperti ekstrak daun nimba atau bawang putih, dapat mengurangi serangan hama pada tanaman kacang tanah tanpa merusak ekosistem. Metode ini dianggap lebih berkelanjutan dibandingkan dengan penggunaan pestisida kimia yang dapat menyebabkan residu berbahaya pada tanaman.

                        Untuk informasi lebih lengkap mengenai teknik penanaman kacang tanah, rotasi tanaman, dan strategi pemeliharaan yang berkelanjutan, Anda dapat mengunduh dokumen tambahan yang telah kami sediakan. Dokumen ini mencakup panduan mendetail serta hasil penelitian terbaru yang dapat membantu Anda dalam meningkatkan kualitas dan kuantitas hasil panen kacang tanah secara efisien.
                    """.trimIndent(),
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    letterSpacing = (-0.24).sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(R.color.black)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }


        if (isAtBottom.value) {
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
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

        // untuk memunculkan notifikasi konfirmasi
        if (showDialog) {
            ConfirmationDialog(
                message = "Apakah Kamu Mau Download?",
                onDismissRequest = { showDialog = false },
                onConfirm = {
                    showDialog = false
                },
                onCancel = {
                    showDialog = false
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMateriDokumenScreen() {
    EdufarmTheme {
        MateriDokumenScreen(
            navController = rememberNavController(),
            id = 1,
            title = "Dokumen Tambahan : Penanaman Kacang Tanah"
        )
    }
}