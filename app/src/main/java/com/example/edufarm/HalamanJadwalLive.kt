package com.example.edufarm

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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.data.model.Jadwal
import com.example.edufarm.data.source.lokal.DummyData
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun JadwalLiveScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf("Live Mentor") }
    var selectedDay by remember { mutableStateOf("Senin") }
    val jadwalList = DummyData.jadwalPerHari[selectedDay] ?: emptyList()
    val daysOfWeek = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")
    var isNotificationActive by remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, selectedItem)
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
                        text = "Oktober 2024",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth() // Pastikan row mengisi layar
                    ) {
                        daysOfWeek.forEach { day ->
                            Box(
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable { selectedDay = day }
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(R.color.green_logo), // Border hijau
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        color = if (day == selectedDay) colorResource(R.color.green) else Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = day,
                                    color = if (day == selectedDay) Color.White else Color.Black,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Teks Hari dan Tanggal
            Text(
                text = "$selectedDay, 19 Okt 2024",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 35.dp, end = 35.dp, bottom = 14.dp)
            )

            // Konten Jadwal atau Pesan Kosong
            if (jadwalList.isEmpty()) {
                // Jika tidak ada jadwal
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 35.dp, end = 35.dp, top = 16.dp, bottom = 16.dp)
                        .border(
                            BorderStroke(1.dp, colorResource(id = R.color.green_logo)),
                            RoundedCornerShape(10.dp)
                        )
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Gambar Kalender
                        Image(
                            painter = painterResource(id = R.drawable.ic_calendar_empty),
                            contentDescription = "Empty Calendar",
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        // Pesan Tidak Ada Jadwal
                        Text(
                            text = "Yahh, Hari ini engga ada live mentor",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(R.color.green_jadwal)
                        )
                    }
                }
            } else {
                // Jika ada jadwal
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 35.dp, end = 35.dp)
                ) {
                    items(jadwalList) { jadwal ->
                        JadwalCard(jadwal, isNotificationActive) { isNotificationActive = it }
                    }
                }
            }
        }
    }
}



@Composable
fun JadwalCard(
    jadwal: Jadwal,
    isNotificationActive: Boolean,
    onNotificationToggle: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, color = colorResource(id = R.color.green_logo)) // Border hijau
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
                        text = jadwal.title,
                        fontFamily = poppinsFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Mentor dan Durasi
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = jadwal.mentor,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
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
                        Text(
                            text = "${calculateDuration(jadwal.timeRange)} jam",
                            fontSize = 14.sp,
                            color = colorResource(R.color.green_logo)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Rentang Waktu
                    Text(
                        text = jadwal.timeRange,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // Ikon Notifikasi di Atas Kanan
        Icon(
            painter = painterResource(
                if (isNotificationActive) R.drawable.notifikasi_aktif else R.drawable.notifikasi_default
            ),
            contentDescription = "Notification",
            tint = colorResource(R.color.green_logo),
            modifier = Modifier
                .align(Alignment.TopEnd) // Posisi atas kanan
                .padding(12.dp) // Posisikan di dalam padding card
                .size(24.dp)
                .clickable { onNotificationToggle(!isNotificationActive) }
        )
    }
}



fun calculateDuration(timeRange: String): Int {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val times = timeRange.split(" - ")
    return if (times.size == 2) {
        val startTime = timeFormat.parse(times[0])
        val endTime = timeFormat.parse(times[1])
        val durationInMillis = endTime.time - startTime.time
        (durationInMillis / (1000 * 60 * 60)).toInt()
    } else {
        0
    }
}


@Preview(showBackground = true)
@Composable
fun JadwalLiveScreenPreview() {
    EdufarmTheme {
        JadwalLiveScreen(
            navController = rememberNavController(),
        )
    }
}
