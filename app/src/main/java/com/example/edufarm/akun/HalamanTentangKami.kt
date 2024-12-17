package com.example.edufarm.akun

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.R
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HalamanTentangKami(navController: NavController, modifier: Modifier = Modifier) {
    val selectedItem = remember { mutableStateOf("Akun") }
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.green)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .background(
                        color = colorResource(id = R.color.green),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
            ) {
                TopBar(
                    title = "Tentang Kami",
                    navController = navController,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 25.dp)
                )
            }
        },
        bottomBar = { BottomNavigationBar(navController, selectedItem) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(id = R.color.background))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo EduFarm",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.green_edu))) {
                            append("Edu")
                        }
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.green_logo))) {
                            append("Farm")
                        }
                    },
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "EduFarm adalah aplikasi edukasi digital yang dirancang untuk membantu petani pemula dan profesional dalam meningkatkan produktivitas pertanian mereka. Aplikasi ini menyediakan panduan, alat bantu, serta informasi terkini mengenai teknik pertanian modern yang pasti ramah lingkungan dan efisien.",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Aplikasi EduFarm diharapkan menjadi solusi komprehensif yang tidak hanya mendukung petani dalam bercocok tanam tetapi juga membantu meningkatkan kesejahteraan mereka secara keseluruhan.\uD83C\uDF31",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Versi 1.0",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.gray_text)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TentangKamiScreenPreview() {
    EdufarmTheme {
        HalamanTentangKami(
            navController = rememberNavController()
        )
    }
}