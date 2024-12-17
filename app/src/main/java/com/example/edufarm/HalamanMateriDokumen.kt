package com.example.edufarm

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.MateriViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MateriDokumenScreen(navController: NavController, kategoriId: Int, modulId: Int) {
    val listState = rememberLazyListState()
    val isAtBottom = remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }
    var showDialog by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)
    val context = LocalContext.current

    val viewModel: MateriViewModel = viewModel()
    val materiList = viewModel.materiList.value
    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(kategoriId) {
        viewModel.fetchMateriByCategory(kategoriId)
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    val currentMateri = materiList.find { it.modul_id == modulId }

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


        if (currentMateri != null) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
            ) {

                item {
                    Text(
                        text = "Dokumen tambahan: ${currentMateri.nama_modul}",
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        letterSpacing = (-0.24).sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        color = colorResource(R.color.black)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = """
                            Menurut jurnal pertanian yang diterbitkan oleh International Journal of Agronomy, persiapan lahan yang baik dapat secara signifikan meningkatkan hasil kacang tanah. Mereka menemukan bahwa tanah yang digemburkan hingga kedalaman 25-30 cm memungkinkan akar tanaman untuk tumbuh lebih dalam, serta meningkatkan retensi air dan drainase. Penanaman kacang tanah juga memerlukan rotasi tanaman dengan tanaman lain seperti jagung atau kedelai untuk menjaga kesuburan tanah dan menghindari kelelahan tanah akibat penanaman monokultur.
                            Sebuah artikel dari Journal of Crop Science menekankan pentingnya memilih waktu tanam yang tepat, yaitu pada musim penghujan, ketika kelembapan tanah cukup tinggi untuk mendukung pertumbuhan benih. Pemberian pupuk organik juga direkomendasikan karena dapat meningkatkan struktur tanah dan menyediakan nutrisi yang lebih seimbang bagi tanaman kacang tanah dibandingkan pupuk kimia.
                            Selain itu, penelitian dari Journal of Soil and Water Conservation menunjukkan bahwa teknik pengairan yang baik sangat penting untuk tanaman kacang tanah. Pengairan yang tidak memadai atau berlebihan dapat merusak kualitas tanaman dan mengurangi hasil panen. Oleh karena itu, menjaga keseimbangan air dalam tanah adalah faktor kunci dalam budidaya kacang tanah yang sukses.
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

            if (showDialog) {
                ConfirmationDialog(
                    message = "Apakah Kamu Mau Download?",
                    onDismissRequest = { showDialog = false },
                    onConfirm = {
                        showDialog = false
                        currentMateri.file?.let { fileUrl ->
                            val driveUrl = "https://drive.google.com/uc?export=download&id=" + extractDriveFileId(fileUrl)
                            downloadFile(context, driveUrl)

                            Log.d("fileId", "fileId: $driveUrl")
                        }
                    },
                    onCancel = {
                        showDialog = false
                    }
                )
            }
        } else {
            Text(
                text = errorMessage ?: "Materi tidak ditemukan.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = poppinsFontFamily,
                color = colorResource(R.color.black)
            )
        }
    }
}

fun downloadFile(context: Context, fileUrl: String) {
    val fileName = "dokumen_${System.currentTimeMillis()}.pdf"
    val request = DownloadManager.Request(Uri.parse(fileUrl))
        .setTitle("Unduh file - $fileName")
        .setMimeType("application/pdf")
        .setDescription("Sedang Mengunduh File ")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)

    Log.d("DownloadManager", "Unduhan dimulai: $fileName")
}


fun extractDriveFileId(url: String): String {
    val cleanedUrl = url.substringAfter("http://localhost:3000/uploads/materi/pdf/")
    return cleanedUrl.substringAfter("https://drive.google.com/file/d/").substringBefore('/')
}





@Preview(showBackground = true)
@Composable
fun PreviewMateriDokumenScreen() {
    EdufarmTheme {
        MateriDokumenScreen(
            navController = rememberNavController(),
            kategoriId = 1,
            modulId = 1
        )
    }
}