package com.example.edufarm
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.ui.theme.EdufarmTheme

class HalamanAwal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdufarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EduFarmScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun EduFarmScreen(modifier: Modifier = Modifier) {
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
                .padding(bottom = 29.dp)
        )
        Row {
            Text(
                text = "Edu",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = colorResource(id = R.color.orange_text),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Farm",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = colorResource(id = R.color.green_logo),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )
        }
        Text(
            text = "Sahabat Petani Modern",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )
    }
}
@Preview(showBackground = true)
@Composable
fun EduFarmScreenPreview() {
    EdufarmTheme {
        EduFarmScreen(modifier = Modifier)
    }
}
