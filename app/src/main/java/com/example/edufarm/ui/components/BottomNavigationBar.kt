package com.example.edufarm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.edufarm.R
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun BottomNavigationBar(
    navController: NavController,
    selectedItem: MutableState<String>
) {
    NavigationBar(
        modifier = Modifier
            .height(61.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = colorResource(id = R.color.white)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(top = 15.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Daftar item navigasi
            val items = listOf(
                Pair("Beranda", Routes.HALAMAN_BERANDA),
                Pair("Live Mentor", Routes.HALAMAN_LIVE_MENTOR),
                Pair("Pelatihan", Routes.HALAMAN_PELATIHAN),
                Pair("Akun", Routes.HALAMAN_AKUN)
            )

            items.forEach { item ->
                val isSelected = selectedItem.value == item.first

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = when (item.first) {
                                "Beranda" -> R.drawable.beranda
                                "Live Mentor" -> R.drawable.vidio
                                "Pelatihan" -> R.drawable.pelatihan
                                "Akun" -> R.drawable.akun
                                else -> R.drawable.beranda// Default icon jika tidak cocok
                            }),
                            contentDescription = item.first,
                            tint = if (isSelected) colorResource(id = R.color.green_icon) else colorResource(R.color.gray_icon),
                            modifier = Modifier.size(width = 24.dp, height = 22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.first,
                            color = if (isSelected) colorResource(id = R.color.green_text) else colorResource(id = R.color.gray_icon),
                            fontSize = 10.sp,
                            lineHeight = 10.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontFamily = poppinsFontFamily
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        // Update item yang dipilih
                        selectedItem.value = item.first

                        // Navigasi ke halaman yang sesuai
                        navController.navigate(item.second) {
                            // Pop ke awal saat navigasi, menghindari duplikasi rute di stack
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

