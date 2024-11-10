package com.example.edufarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily


@Composable
fun LiveMentorScreen(
    navController: NavController, modifier: Modifier = Modifier
) {
    val selectedItem = remember { mutableStateOf("Live Mentor") }

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(R.color.background))
                .padding(start = 35.dp, end = 35.dp, top = 5.dp)
                .verticalScroll(rememberScrollState())
        ) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Jadwal Live Hari ini",
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

            CardLiveMentor()
            Spacer(modifier = Modifier.height(16.dp))

            LiveMentorDescription()

        }
    }
}

@Composable
fun LiveMentorDescription() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Yuk, Bertani Gandum Bareng! 🎉",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Live Mentor: Bertanam Gandum 🌾",
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Belajar menanam gandum dari ahlinya! 🌱 Simak tips dan trik bertanam gandum yang benar, mulai dari pemilihan benih, pengolahan tanah, hingga perawatannya.",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )

        Spacer(modifier = Modifier.height(16.dp))

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


        val benefits = listOf(
            "Panduan praktis: Belajar langsung dari pakar pertanian berpengalaman.",
            "Sharing pengalaman: Berdiskusi dan berbagi pengalaman dengan petani lainnya.",
            "Tanya jawab: Ajukan pertanyaan tentang menanam gandum sepuasnya.",
            "Motivasi dan inspirasi: Dapatkan semangat dan inspirasi untuk memulai bertani."
        )

        benefits.forEach { benefit ->
            Text(
                text = "- $benefit",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                lineHeight = 20.sp,
                letterSpacing = (-0.24).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Gabung sekarang dan raih hasil panen yang maksimal! Jangan lewatkan kesempatan emas ini! 🎉",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )
    }
}

@Composable
fun CardLiveMentor() {
    var showDialog by remember { mutableStateOf(false) }
    var isNotificationActive by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.green),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(1.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_notif))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Bertanam Gandum",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 16.sp,
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
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.live),
                        contentDescription = null,
                        modifier = Modifier.size(width = 26.dp, height = 22.dp)
                    )
                    Text(
                        text = "Sedang Berlangsung",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily,
                        color = colorResource(id = R.color.green_title)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "waktu",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.green_title),
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                        Text(
                            text = "09.30–12.30",
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
                            text = "Vodka",
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
            onConfirm = {
                showDialog = false
            },
            onCancel = {
                showDialog = false
            }
        )
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewLiveMentorScreen() {
    EdufarmTheme {
        LiveMentorScreen(
            navController = rememberNavController(), modifier = Modifier
        )
    }
}