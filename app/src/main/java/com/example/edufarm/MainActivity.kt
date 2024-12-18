package com.example.edufarm

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.navigation.EdufarmNavHost
import com.example.edufarm.ui.theme.EdufarmTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdufarmTheme {
                val navController = rememberNavController()
                Surface {
                    EdufarmNavHost(
                        navController = navController
                    )
                }
            }
        }
    }
}
