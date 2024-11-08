package com.example.edufarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily


@Composable
fun ProfileScreen(
    navController: NavController
) {
    val selectedItem = remember { mutableStateOf("Akun") }

    Scaffold(
        modifier = Modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.background))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
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
                Spacer(modifier = Modifier.weight(1f))
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
                    text = "Privasi & Keamanan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.black)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.row),
                    contentDescription = "Row",
                    modifier = Modifier.size(width = 23.dp, height = 23.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

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
                Spacer(modifier = Modifier.weight(1f))
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
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.row),
                    contentDescription = "Row",
                    modifier = Modifier.size(width = 23.dp, height = 23.dp)
                )
            }
            Spacer(modifier = Modifier.height(60.dp))

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
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Keluar",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    EdufarmTheme {
        ProfileScreen(
            navController = rememberNavController()
        )
    }
}