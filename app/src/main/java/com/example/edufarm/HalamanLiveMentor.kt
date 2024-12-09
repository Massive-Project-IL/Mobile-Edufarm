package com.example.edufarm

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.data.model.JadwalLive
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.JadwalLiveViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LiveMentorScreen(
    navController: NavController,
    viewModel: JadwalLiveViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // Format tanggal "dd" untuk menampilkan tanggal hari ini
    val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd"))

    // State untuk memilih item menu
    val selectedItem = remember { mutableStateOf("Live Mentor") }

    // State untuk menyimpan ID jadwal yang dipilih
    val selectedJadwalId = remember { mutableStateOf<Int?>(null) }

    // Mengatur warna status bar
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    // Mendapatkan data jadwal dan pesan error dari ViewModel
    val jadwalList by viewModel.jadwalLive.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Menetapkan warna status bar setelah komponen dipasang
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(color = topBarColor, darkIcons = true)
        viewModel.fetchJadwalLive() // Mengambil data jadwal live
    }

    val filteredJadwalList = jadwalList.filter { jadwal ->
        val jadwalDate = convertUtcToLocalDateTime(jadwal.tanggal).toLocalDate()  // Konversi UTC ke tanggal lokal
        val currentDate = LocalDate.parse(getCurrentDate()) // Mengonversi currentDate menjadi LocalDate

        Log.d("LiveMentorScreen", "Comparing: jadwalDate = $jadwalDate, currentDate = $currentDate")

        jadwalDate == currentDate // Membandingkan tanggal jadwal dengan tanggal saat ini
    }

    // Jika belum ada jadwal yang dipilih, pilih ID jadwal pertama
    if (selectedJadwalId.value == null && filteredJadwalList.isNotEmpty()) {
        selectedJadwalId.value = filteredJadwalList.first().notifikasi_id
    }

    // Scaffold untuk layout
    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(R.color.background))
                .padding(start = 35.dp, end = 35.dp, top = 5.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Judul Live Mentor
            Text(
                text = "Live Mentor",
                fontSize = 18.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Teks untuk jadwal live hari ini
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jadwal Live Hari ini - Tanggal $todayDate",
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 23.sp,
                    letterSpacing = 0.02.em,
                    color = Color.Black
                )
                Button(
                    onClick = { navController.navigate(Routes.HALAMAN_JADWAL_LIVE) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.green)
                    ),
                    contentPadding = PaddingValues(horizontal = 1.dp, vertical = 0.dp),
                    modifier = Modifier
                        .width(84.dp)
                        .height(25.dp)
                ) {
                    Text(
                        text = "Lihat Jadwal",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.02.em,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tampilkan error jika ada
            if (errorMessage?.isNotEmpty() == true) {
                Text(
                    text = errorMessage!!,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Tampilkan gambar dan deskripsi jika tidak ada jadwal live mentor
            if (filteredJadwalList.isEmpty()) {
                NoJadwalCard() // Komponen yang menampilkan card jika tidak ada jadwal
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                // LazyRow untuk menampilkan card secara horizontal
                LazyRow(
                    contentPadding = PaddingValues(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredJadwalList) { jadwal ->
                        CardLiveMentor(jadwal = jadwal, onCardClick = {
                            selectedJadwalId.value = jadwal.notifikasi_id // Set ID jadwal yang dipilih
                        })
                    }
                }
            }

            // Menampilkan deskripsi dan data berdasarkan ID yang dipilih
            LiveMentorDescription(
                jadwalList = filteredJadwalList,
                selectedJadwalId = selectedJadwalId.value
            )
        }
    }
}


// Fungsi untuk mengonversi UTC ke LocalDateTime sesuai zona waktu Jakarta
@RequiresApi(Build.VERSION_CODES.O)
fun convertUtcToLocalDateTime(utcDate: String): LocalDateTime {
    val utcDateTime = ZonedDateTime.parse(utcDate)  // Parsing string UTC ke ZonedDateTime
    val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Jakarta")).toLocalDateTime()  // Konversi ke waktu lokal
    return localDateTime
}

// Fungsi untuk mendapatkan tanggal hari ini
@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    return LocalDate.now().toString() // Mengambil tanggal hari ini dalam format YYYY-MM-DD
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardLiveMentor(jadwal: JadwalLive, onCardClick: (Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var isNotificationActive by remember { mutableStateOf(false) }

    // Mengonversi waktu dan menghilangkan detik
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val formattedStartTime = LocalTime.parse(jadwal.waktu_mulai).format(timeFormatter)
    val formattedEndTime = LocalTime.parse(jadwal.waktu_selesai).format(timeFormatter)

    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(300.dp)
            .border(
                BorderStroke(1.dp, colorResource(id = R.color.green_logo)),
                RoundedCornerShape(16.dp)
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .clickable { onCardClick(jadwal.notifikasi_id) }, // Menambahkan onClick untuk card
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_notif)) // Gunakan warna berbeda untuk card
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = jadwal.judul_notifikasi,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.green_title)
                    )
                    Icon(
                        painter = painterResource(
                            if (isNotificationActive) R.drawable.notifikasi_default else R.drawable.notifikasi_aktif
                        ),
                        contentDescription = "Notifikasi",
                        tint = colorResource(id = R.color.green_title),
                        modifier = Modifier
                            .size(26.dp)
                            .clickable { isNotificationActive = !isNotificationActive }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Waktu",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.green_title),
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                        Text(
                            text = "$formattedStartTime – $formattedEndTime", // Menampilkan jam dan menit
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.green_title)
                        )
                    }
                    Column {
                        Text(
                            text = "Nama Mentor",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.green_title),
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                        Text(
                            text = jadwal.nama_mentor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.green_title)
                        )
                    }
                    Button(
                        onClick = { showDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                        modifier = Modifier
                            .width(93.dp)
                            .height(30.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Gabung Live",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsFontFamily
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        ConfirmationDialog(
            message = "Apakah Kamu Mau Gabung Live?",
            onDismissRequest = { showDialog = false },
            onConfirm = { showDialog = false },
            onCancel = { showDialog = false }
        )
    }
}


@Composable
fun NoJadwalCard() {
    Box(
        modifier = Modifier
            .border(
                BorderStroke(1.dp, colorResource(id = R.color.green_logo)),
                RoundedCornerShape(16.dp)
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),  // Menempatkan Card di tengah
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_notif))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()  // Memastikan Column mengisi seluruh ruang di dalam Card
                    .padding(16.dp)  // Menambahkan padding di dalam Column
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar_empty),
                    contentDescription = "Empty Calendar",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "Yahh, Tidak ada live mentor untuk Hari ini",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(R.color.green_jadwal)
                )
            }
        }
    }
}

@Composable
fun LiveMentorDescription(
    jadwalList: List<JadwalLive>,
    selectedJadwalId: Int?
) {
    val selectedJadwal = jadwalList.find { it.notifikasi_id == selectedJadwalId }

    Column(modifier = Modifier.fillMaxWidth()) {
        if (selectedJadwal == null) {
            Text(
                text = "Yahh , Hari ini engga ada live mentor. Kamu bisa coba pelajari materi yang sudah ada di bagian Pelatihan.\n\nSemangattt berlatih 🙌 🌾",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp,
                modifier = Modifier.padding(top = 20.dp)
            )
        } else {
            // Tampilkan kategori
            Text(
                text = "Yuk, Bertani ${selectedJadwal.nama_kategori} Bareng! 🎉",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Menampilkan judul notifikasi
            Text(
                text = "Live Mentor: ${selectedJadwal.judul_notifikasi}",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Menampilkan deskripsi dari jadwal
            Text(
                text = selectedJadwal.deskripsi,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Menampilkan poin-poin yang akan didapat
            Text(
                text = "Yang akan kamu dapatkan:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Memecah string "poin" menjadi list dan menampilkan setiap poin
            val benefits = selectedJadwal.poin.split("\n")

            benefits.forEach { benefit ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FiberManualRecord, // Ikon bullet
                        contentDescription = "Bullet Point",
                        tint = Color.Black,
                        modifier = Modifier.size(8.dp) // Ukuran ikon bullet
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = benefit,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        lineHeight = 20.sp,
                        letterSpacing = (-0.24).sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Menampilkan pesan ajakan
            Text(
                text = "Gabung sekarang dan raih hasil panen yang maksimal! Jangan lewatkan kesempatan emas ini! 🎉",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}





@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewLiveMentorScreen() {
    EdufarmTheme {
        LiveMentorScreen(
            navController = rememberNavController(), modifier = Modifier
        )
    }
}

