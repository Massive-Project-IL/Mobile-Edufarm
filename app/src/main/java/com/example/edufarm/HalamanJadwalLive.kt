package com.example.edufarm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun JadwalLiveScreen(navController: NavController, modifier: Modifier) {
    val selectedItem = remember { mutableStateOf("Live Mentor") }
    var selectedDay by remember { mutableStateOf("Senin") }
    val daysOfWeek = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")
    var isNotificationActive by remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = false
        )
    }

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(R.color.background))
                .padding(start = 35.dp, end = 35.dp, top = 5.dp)
        ) {
            TopBar(
                title = "Jadwal Live",
                navController = navController
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Oktober 2024",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 1.dp, top = 8.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
            ) {
                daysOfWeek.forEach { day ->
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable { selectedDay = day }
                            .border(
                                width = 1.dp,
                                color = if (day == selectedDay) colorResource(id = R.color.green) else colorResource(
                                    id = R.color.green
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = if (day == selectedDay) colorResource(id = R.color.green) else Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = day,
                            modifier = Modifier.padding(
                                vertical = 7.dp,
                                horizontal = 11.dp
                            ),
                            color = if (day == selectedDay) Color.White else Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "$selectedDay, 14 Okt 2024",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, colorResource(id = R.color.green_logo), RoundedCornerShape(10.dp))
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Bertanam Gandum",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Vodka  •  3 jam",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.green_logo)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "09:30:00 - 12:30:00",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Icon(
                    painter = painterResource(
                        if (isNotificationActive) R.drawable.notifikasi_aktif else R.drawable.notifikasi_default
                    ),
                    contentDescription = "Notification",
                    tint = colorResource(id = R.color.green_logo),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .clickable { isNotificationActive = !isNotificationActive }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JadwalLiveScreenPreview() {
    EdufarmTheme {
        JadwalLiveScreen(
            navController = rememberNavController(),
            modifier = Modifier
        )
    }
}
