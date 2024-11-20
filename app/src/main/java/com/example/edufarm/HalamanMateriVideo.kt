package com.example.edufarm

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme

@Composable
fun MateriVideoScreen(navController: NavController) {
    var isPlaying by remember { mutableStateOf(false) }
    var isFullscreen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val videoUri = Uri.parse("android.resource://${context.packageName}/${R.raw.video_gandum}")

    // Kontrol orientasi layar
    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(isFullscreen) {
        (context as? Activity)?.let { activity ->
            activity.requestedOrientation = if (isFullscreen) {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE // Mendukung landscape otomatis
            } else {
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED // Kembali ke orientasi default
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
    ) {
        // Tampilkan TopBar jika tidak dalam fullscreen
        if (!isFullscreen) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TopBar(
                    navController = navController,
                    title = "Materi"
                )
            }
        }

        // Area video
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .padding(if (isFullscreen) 16.dp else 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // VideoView dan kontrol fullscreen
            if (isPlaying) {
                AndroidView(
                    factory = { ctx ->
                        VideoView(ctx).apply {
                            setVideoURI(videoUri)
                            setOnPreparedListener { mediaPlayer ->
                                mediaPlayer.isLooping = true
                                start() // Video mulai setelah tombol play ditekan
                            }

                            // Tambahkan MediaController untuk kontrol video
                            val mediaController = MediaController(ctx).apply {
                                setAnchorView(this@apply)
                            }
                            setMediaController(mediaController)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(if (isFullscreen) RoundedCornerShape(0.dp) else RoundedCornerShape(16.dp))
                )

                // Tombol fullscreen toggle di area video
                Icon(
                    painter = painterResource(id = if (isFullscreen) R.drawable.ic_exit_fullscreen else R.drawable.ic_fullscreen),
                    contentDescription = "Fullscreen Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Posisi tetap di pojok bawah kanan
                        .padding(16.dp)
                        .size(32.dp)
                        .clickable {
                            isFullscreen = !isFullscreen
                        }
                )
            } else {
                // Thumbnail sebelum video diputar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.petani),
                        contentDescription = "Thumbnail Video Materi",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    // Tombol Play
                    Icon(
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = "Play Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(54.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.7f),
                                shape = CircleShape
                            )
                            .clickable {
                                isPlaying = true
                            }
                            .padding(16.dp)
                    )

                    // Tombol fullscreen langsung dari thumbnail
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fullscreen),
                        contentDescription = "Fullscreen Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(32.dp)
                            .clickable {
                                isFullscreen = true // Masuk fullscreen langsung dari thumbnail
                            }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMateriVideoScreen() {
    EdufarmTheme {
        MateriVideoScreen(navController = rememberNavController())
    }
}

