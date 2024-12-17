package com.example.edufarm

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.data.model.JadwalLive
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.JadwalLiveViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JadwalLiveScreen(navController: NavController, viewModel: JadwalLiveViewModel = viewModel()) {
    val today = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(today.dayOfMonth.toString().padStart(2, '0')) }
    val currentMonth = today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val currentYear = today.year.toString()
    val datesOfMonth = (1..today.lengthOfMonth()).map { it.toString().padStart(2, '0') }
    val jadwalList by viewModel.jadwalLive.collectAsState()
    var activeNotificationIds by remember { mutableStateOf(setOf<Int>()) }

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
        // Memuat data jadwal live dari ViewModel
        viewModel.fetchJadwalLive()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, remember { mutableStateOf("Live Mentor") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(R.color.background))
        ) {
            TopBar(
                title = "Jadwal Live",
                navController = navController,
                modifier = Modifier.padding(start = 35.dp, end = 35.dp, top = 5.dp, bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.gray_live))
            ) {
                Column(
                    modifier = Modifier.padding(start = 35.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "$currentMonth $currentYear",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth()
                    ) {
                        datesOfMonth.forEach { date ->
                            Box(
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable { selectedDate = date }
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(R.color.green_logo),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        color = if (date == selectedDate) colorResource(R.color.green) else Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = date,
                                    color = if (date == selectedDate) Color.White else Color.Black,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = poppinsFontFamily
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$selectedDate $currentMonth $currentYear",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(start = 35.dp, end = 35.dp)
            )

            // Filter jadwal sesuai tanggal yang dipilih
            val filteredJadwalList = jadwalList.filter { jadwal ->
                try {
                    // Menggunakan SimpleDateFormat untuk parsing tanggal
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    dateFormat.timeZone = TimeZone.getTimeZone("UTC") // Pastikan parsing dalam zona waktu UTC
                    val jadwalDate: Date = dateFormat.parse(jadwal.tanggal) ?: Date() // Fallback ke Date() jika null

                    // Konversi ke zona waktu lokal
                    val localDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    localDateFormat.timeZone = TimeZone.getDefault() // Zona waktu lokal

                    // Gunakan Calendar untuk ekstraksi informasi tanggal
                    val calendar = Calendar.getInstance()
                    calendar.time = jadwalDate

                    // Bandingkan tanggal, bulan, dan tahun
                    calendar.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0') == selectedDate &&
                            calendar.get(Calendar.MONTH) + 1 == today.monthValue &&
                            calendar.get(Calendar.YEAR) == today.year
                } catch (e: Exception) {
                    false
                }
            }

            if (filteredJadwalList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 35.dp, end = 35.dp, top = 16.dp, bottom = 16.dp)
                        .border(
                            BorderStroke(1.dp, colorResource(id = R.color.green_logo)),
                            RoundedCornerShape(10.dp)
                        )
                        .shadow(elevation = 6.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_calendar_empty),
                            contentDescription = "Empty Calendar",
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Yahh, Tidak ada live mentor untuk tanggal $selectedDate",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(R.color.green_jadwal)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 35.dp, end = 35.dp)
                ) {
                    items(filteredJadwalList) { jadwal ->
                        JadwalCard(
                            jadwal = jadwal,
                            isNotificationActive = activeNotificationIds.contains(jadwal.notifikasi_id),
                            onNotificationToggle = { isActive ->
                                activeNotificationIds = if (isActive) {
                                    activeNotificationIds + jadwal.notifikasi_id
                                } else {
                                    activeNotificationIds - jadwal.notifikasi_id
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

// Fungsi untuk mengonversi durasi dalam format HH:mm:ss menjadi jam dan menit
fun convertDurationToString(duration: String): String {
    val timeParts = duration.split(":") // Memisahkan jam, menit, detik
    val hours = timeParts[0].toInt() // Ambil bagian jam
    val minutes = timeParts[1].toInt() // Ambil bagian menit

    return when {
        hours > 0 && minutes > 0 -> "$hours jam $minutes menit"
        hours > 0 -> "$hours jam"
        minutes > 0 -> "$minutes menit"
        else -> "0 menit"
    }
}

@Composable
fun JadwalCard(
    jadwal: JadwalLive,
    isNotificationActive: Boolean,
    onNotificationToggle: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Card utama
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, color = colorResource(id = R.color.green_logo))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Judul
                    Text(
                        text = jadwal.judul_notifikasi,
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    // Nama Mentor dan Durasi
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = jadwal.nama_mentor,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    color = colorResource(R.color.green_logo),
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        // Durasi
                        val formattedDuration = convertDurationToString(jadwal.durasi)
                        Text(
                            text = formattedDuration,
                            fontSize = 12.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(R.color.green_logo)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Waktu Mulai dan Selesai
                    Text(
                        text = "${jadwal.waktu_mulai} - ${jadwal.waktu_selesai}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // Ikon notifikasi
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(
                    if (isNotificationActive) R.drawable.notifikasi_aktif else R.drawable.notifikasi_default
                ),
                contentDescription = "Notification",
                tint = colorResource(R.color.green_logo),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNotificationToggle(!isNotificationActive) }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewJadwalLiveScreen() {
    EdufarmTheme {
        JadwalLiveScreen(
            navController = rememberNavController()
        )
    }
}
