package com.example.edufarm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.R
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                color = if (isSelected) colorResource(R.color.green)
                else colorResource(R.color.white),
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = colorResource(R.color.green),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = category,
            color = if (isSelected) colorResource(R.color.white)
            else colorResource(R.color.gray_bookmark),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            lineHeight = 20.sp,
            letterSpacing = (-0.24).sp
        )
    }
}