package com.example.edufarm

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.viewModel.MateriViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class FullScreenWebChromeClient(private val activity: Activity) : WebChromeClient() {
    private var customView: View? = null
    private var customViewCallback: CustomViewCallback? = null
    private var originalOrientation: Int = activity.requestedOrientation

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        if (customView != null) {
            onHideCustomView()
            return
        }
        customView = view
        customViewCallback = callback
        originalOrientation = activity.requestedOrientation
        (activity.window.decorView as FrameLayout).addView(
            customView,
            FrameLayout.LayoutParams(-1, -1)
        )
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }

    override fun onHideCustomView() {
        (activity.window.decorView as FrameLayout).removeView(customView)
        customView = null
        customViewCallback?.onCustomViewHidden()
        customViewCallback = null
        activity.requestedOrientation = originalOrientation
    }
}

@Composable
fun MateriVideoScreen(
    navController: NavController,
    kategoriId: Int,
    modulId: Int
) {
    var isPlaying by remember { mutableStateOf(false) }
    val isFullscreen by remember { mutableStateOf(false) }
    val context = LocalContext.current as Activity
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    val viewModel: MateriViewModel = viewModel()
    val materiList by viewModel.materiList.collectAsState(initial = emptyList())
    var videoUrl by remember { mutableStateOf("") }

    LaunchedEffect(kategoriId) {
        viewModel.fetchMateriByCategory(kategoriId)
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    LaunchedEffect(materiList) {
        materiList.find { it.modul_id == modulId }?.let { materi ->
            videoUrl = processVideoUrl(materi.video.toString())
            Log.d("Video", "Processed Video URL: $videoUrl")
        }
    }

    LaunchedEffect(isFullscreen) {
        context.requestedOrientation = if (isFullscreen) {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        systemUiController.isStatusBarVisible = !isFullscreen
        systemUiController.isNavigationBarVisible = !isFullscreen
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {
        if (!isFullscreen) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TopBar(navController = navController, title = "Materi")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isPlaying && videoUrl.isNotEmpty()) {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.loadsImagesAutomatically = true
                            settings.setSupportZoom(true)
                            settings.builtInZoomControls = true
                            settings.displayZoomControls = false
                            settings.useWideViewPort = true
                            settings.loadWithOverviewMode = true
                            webChromeClient = FullScreenWebChromeClient(context)
                            webViewClient = WebViewClient()
                            loadUrl(videoUrl)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(if (isFullscreen) RoundedCornerShape(0.dp) else RoundedCornerShape(16.dp))
                )
            } else {
                isPlaying = true
            }
        }
    }
}

//@Composable
//fun DisplayThumbnail(onClickPlay: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(16 / 9f)
//            .clip(RoundedCornerShape(16.dp))
//            .background(Color.Black),
//        contentAlignment = Alignment.Center
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.petani),
//            contentDescription = "Thumbnail Video Materi",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//        Icon(
//            painter = painterResource(id = R.drawable.ic_play),
//            contentDescription = "Play Icon",
//            tint = Color.White,
//            modifier = Modifier
//                .size(54.dp)
//                .background(color = Color.Black.copy(alpha = 0.7f), shape = CircleShape)
//                .clickable { onClickPlay() }
//                .padding(16.dp)
//        )
//    }
//}

fun processVideoUrl(rawVideoUrl: String): String {
    return when {
        rawVideoUrl.startsWith("http://10.0.2.2:3000/uploads/materi/videos/") -> {
            rawVideoUrl.removePrefix("http://10.0.2.2:3000/uploads/materi/videos/")
        }

        rawVideoUrl.startsWith("https://drive.google.com/file/d/") -> {
            val fileId = rawVideoUrl
                .substringAfter("https://drive.google.com/file/d/")
                .substringBefore("/")
            "https://drive.google.com/file/d/$fileId/preview"
        }

        else -> rawVideoUrl
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMateriVideoScreen() {
    EdufarmTheme {
        MateriVideoScreen(navController = rememberNavController(), kategoriId = 1, modulId = 1)
    }
}