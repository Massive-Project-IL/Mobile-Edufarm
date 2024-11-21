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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.roundToInt

@Composable
fun EduFarmScreen(navController: NavController) {
    val alphaAnim = remember { Animatable(0f) }
    val scaleAnim = remember { Animatable(0.8f) }
    val bounceAnim = remember { Animatable(1.2f) }
    val translateAnim = remember { Animatable(30f) }

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)


    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
        // Bounce animation on logo
        bounceAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )

        // Simultaneous alpha and scale animation
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

        // Delay for splash screen
        kotlinx.coroutines.delay(2000)

        // Navigate to login screen
        navController.navigate(Routes.HALAMAN_LOGIN) {
            popUpTo(Routes.HALAMAN_SPLASH) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Edu Farm",
            modifier = Modifier
                .size(200.dp)
                .scale(scaleAnim.value * bounceAnim.value)
                .alpha(alphaAnim.value)
                .offset { IntOffset(x = 0, y = translateAnim.value.roundToInt()) }
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
                color = colorResource(id = R.color.green_edu),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(alphaAnim.value)
                    .offset { IntOffset(x = 0, y = translateAnim.value.roundToInt()) }
            )
            Text(
                text = "Farm",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = colorResource(id = R.color.green_logo),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .alpha(alphaAnim.value)
                    .offset { IntOffset(x = 0, y = translateAnim.value.roundToInt()) }
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
                .alpha(alphaAnim.value)
                .offset { IntOffset(x = 0, y = translateAnim.value.roundToInt()) }
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
