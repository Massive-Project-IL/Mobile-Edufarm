package com.example.edufarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

class HalamanAkun : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdufarmTheme {
                ProfileScreen()
                }
            }
        }
    }

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val selectedItem = remember { mutableStateOf("Akun") }

    Scaffold(
        modifier = Modifier,
        bottomBar = { BottomNavigationBar(selectedItem) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.background))
                .padding(paddingValues)
                .padding(horizontal = 37.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Akun",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily
            )
            Spacer(modifier = Modifier.height(25.dp))

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(color = Color.Gray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fotoprofil),
                    contentDescription = "Foto Profil",
                    modifier = Modifier.size(width = 110.dp, height = 110.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Petani Muda",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily
            )
            Spacer(modifier = Modifier.height(38.dp))
            Text(
                text = "ablank123@gmail.com",
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = poppinsFontFamily
            )
            Text(
                text = "+1 515 599 9655",
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = poppinsFontFamily
            )

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.pembatas),
                contentDescription = "Pembatas",
                modifier = Modifier.size(width = 319.dp, height = 2.dp)
            )
            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Profil",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit",
                    modifier = Modifier.size(width = 32.dp, height = 32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.black)
                )
                Spacer(modifier = Modifier.width(227.dp))
                Image(
                    painter = painterResource(id = R.drawable.row),
                    contentDescription = "Row",
                    modifier = Modifier.size(width = 23.dp, height = 23.dp)
                )
            }
            Spacer(modifier = Modifier.height(21.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_keamanan),
                    contentDescription = "Privasi & Keamanan",
                    modifier = Modifier.size(width = 32.dp, height = 32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Privasi & Kemananan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.black)
                )
                Spacer(modifier = Modifier.width(95.dp))
                Image(
                    painter = painterResource(id = R.drawable.row),
                    contentDescription = "Row",
                    modifier = Modifier.size(width = 23.dp, height = 23.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Settings
            Text(
                text = "Pengaturan",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily
            )
            Spacer(modifier = Modifier.height(21.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dukungan),
                    contentDescription = "Dukungan",
                    modifier = Modifier.size(width = 32.dp, height = 32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Dukungan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.black)
                )
                Spacer(modifier = Modifier.width(178.dp))
                Image(
                    painter = painterResource(id = R.drawable.row),
                    contentDescription = "Row",
                    modifier = Modifier.size(width = 23.dp, height = 23.dp)
                )
            }
            Spacer(modifier = Modifier.height(21.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_aboutus),
                    contentDescription = "About Us",
                    modifier = Modifier.size(width = 32.dp, height = 32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tentang Kami",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.black)
                )
                Spacer(modifier = Modifier.width(150.dp))
                Image(
                    painter = painterResource(id = R.drawable.row),
                    contentDescription = "Row",
                    modifier = Modifier.size(width = 23.dp, height = 23.dp)
                )
            }
            Spacer(modifier = Modifier.height(110.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_keluar),
                    contentDescription = "Keluar",
                    modifier = Modifier.size(width = 14.86.dp, height = 16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Keluar",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedItem: MutableState<String>) {
    NavigationBar(
        modifier = Modifier
            .height(61.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = colorResource(id = R.color.white)
    ) {
        Row (
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(top = 15.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.beranda),
                        contentDescription = "Beranda",
                        tint = Color.Gray,
                        modifier = Modifier.size(width = 24.dp, height = 22.dp)
                        )
                },
                label = {
                    Text(
                        text = "Beranda",
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
                        painter = painterResource(id = R.drawable.akun),
                        contentDescription = "Home",
                        tint = colorResource(id = R.color.green),
                        modifier = Modifier.size(width = 22.dp, height = 22.dp)
                        )
                },
                label = {
                    Text(
                        text = "Akun",
                        color = colorResource(id = R.color.green),
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily
                    )
                },
                selected = false,
                onClick = { /* Navigasi ke halaman Akun */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    EdufarmTheme {
        ProfileScreen()
    }
}