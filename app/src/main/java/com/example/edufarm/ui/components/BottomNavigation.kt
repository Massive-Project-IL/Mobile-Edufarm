package com.example.edufarm.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.R
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun BottomNavigationBarPelatihan(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        NavigationBar(
            modifier = Modifier
                .height(61.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            containerColor = colorResource(id = R.color.white)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .padding(top = 15.dp, bottom = 10.dp)
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Home",
                            tint = colorResource(id = R.color.green),
                        )
                    },
                    label = {
                        Text(
                            text = "Beranda",
                            color = colorResource(id = R.color.green),
                            fontSize = 10.sp,
                            lineHeight = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsFontFamily
                        )
                    },
                    selected = false,
                    onClick = { /* halaman Beranda */ }
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.vidio),
                            contentDescription = "Live Mentor",
                            tint = Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Live Mentor",
                            color = Color.Gray,
                            fontSize = 10.sp,
                            lineHeight = 10.sp
                        )
                    },
                    selected = false,
                    onClick = { /* Navigasi ke halaman Live Mentor */ }
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.pelatihan),
                            contentDescription = "Pelatihan",
                            tint = Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Pelatihan",
                            color = Color.Gray,
                            fontSize = 10.sp,
                            lineHeight = 10.sp
                        )
                    },
                    selected = false,
                    onClick = { /* Navigasi ke halaman Pelatihan */ }
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.profil),
                            contentDescription = "Akun",
                            tint = Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Akun",
                            color = Color.Gray,
                            fontSize = 10.sp,
                            lineHeight = 10.sp
                        )
                    },
                    selected = false,
                    onClick = { /* Navigasi ke halaman Akun */ }
                )
            }
        }
    }
}