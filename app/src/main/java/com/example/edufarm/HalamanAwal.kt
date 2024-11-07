package com.example.edufarm

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import kotlin.math.roundToInt


@Composable
fun EduFarmScreen(navController: NavController) {
    // State untuk mengatur level opacity, skala, dan posisi animasi
    val alphaAnim = remember { Animatable(0f) }
    val scaleAnim = remember { Animatable(0.8f) }
    val translateAnim = remember { Animatable(30f) } // Animasi translasi ke atas

    // Animasi fade-in, scale-up, dan translasi
    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
        translateAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )

        // Delay sebelum beralih ke halaman utama
        kotlinx.coroutines.delay(2000)

        navController.navigate(Routes.HALAMAN_LOGIN) {
            popUpTo(Routes.HALAMAN_SPLASH) { inclusive = true }
        }
    }

    // Tampilan Splash Screen dengan animasi fade-in, scale-up, dan translasi
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baru_2),
            contentDescription = "Edu Farm",
            modifier = Modifier
                .size(200.dp)
                .scale(scaleAnim.value)   // Animasi scale-up pada gambar
                .alpha(alphaAnim.value)   // Animasi fade-in pada gambar
                .offset { IntOffset(x = 0, y = translateAnim.value.roundToInt()) } // Memperbaiki penggunaan offset
                .padding(bottom = 29.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Edu",
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                fontSize = 30.sp,
                color = colorResource(id = R.color.orange_text),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(alphaAnim.value)    // Animasi fade-in pada teks
                    .offset { IntOffset(x = 0, y = translateAnim.value.roundToInt()) }  // Memperbaiki penggunaan offset
            )
            Text(
                text = "Farm",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = colorResource(id = R.color.green_logo),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 4.dp)    // Menambahkan sedikit padding untuk perataan
                    .alpha(alphaAnim.value)   // Animasi fade-in pada teks
                    .offset { IntOffset(x = 0, y = translateAnim.value.roundToInt()) }  // Memperbaiki penggunaan offset
            )
        }
        Text(
            text = "Sahabat Petani Modern",
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            fontSize = 14.sp,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .alpha(alphaAnim.value)    // Animasi fade-in pada teks
                .offset { IntOffset(x = 0, y = translateAnim.value.roundToInt()) }  // Memperbaiki penggunaan offset
        )
    }
}




@Preview(showBackground = true)
@Composable
fun EduFarmScreenPreview() {
    EdufarmTheme {
        EduFarmScreen(
            navController = rememberNavController()
        )
    }
}
