package com.example.edufarm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun ErrorMessages(errors: List<String>) {
    if (errors.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.Red.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Column {
                errors.forEach { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}