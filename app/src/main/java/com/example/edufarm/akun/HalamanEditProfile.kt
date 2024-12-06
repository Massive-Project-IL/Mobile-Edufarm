package com.example.edufarm.akun

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.R
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HalamanEditProfile(navController: NavController, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("petanimuda@gmail.com") }
    var phoneNumber by remember { mutableStateOf("") }
    val selectedItem = remember { mutableStateOf("Akun") }
    var showPopup by remember { mutableStateOf(false) }
    var showBottomNav by remember { mutableStateOf(true) }

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.green)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomNav) { // Tampilkan BottomNavigation hanya jika showBottomNav = true
                BottomNavigationBar(navController, selectedItem)
            }
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background))
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        color = colorResource(id = R.color.green),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
            ) {
                TopBar(
                    title = "Edit Profile",
                    navController = navController,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-50).dp)
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .background(Color.White, CircleShape)
                            .padding(2.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fotoprofil),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    // Camera Icon
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .offset(x = 6.dp, y = 6.dp)
                            .background(
                                color = colorResource(id = R.color.green_logo),
                                shape = CircleShape
                            )
                            .clickable {
                                showPopup = true
                                showBottomNav = false // Sembunyikan Bottom Navigation
                            }

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Edit Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .offset(x = 4.dp, y = 4.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Nama",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                BasicTextField(
                    value = name,
                    onValueChange = { name = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.green_logo),
                            RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 16.dp),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (name.isEmpty()) {
                                Text(
                                    text = "Masukan Nama Lengkap Baru",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Email",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.green_logo),
                            RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 16.dp),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Nomor Telepon",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                BasicTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.green_logo),
                            RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 16.dp),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (phoneNumber.isEmpty()) {
                                Text(
                                    text = "Masukan Nomor Telepon Baru",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(60.dp))



                // Save Changes Button
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "Simpan Perubahan",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        if (showDialog) {
            ConfirmationDialog(
                message = "Apakah Kamu Yakin Ingin Mengubahnya?",
                onDismissRequest = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    // Lakukan aksi simpan data
                },
                onCancel = { showDialog = false }
            )
        }

        if (showPopup) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),

                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    elevation = CardDefaults.elevatedCardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                )  {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.close),
                                contentDescription = "Close Icon",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        showPopup = false
                                        showBottomNav = true
                                    }

                            )
                            Spacer(modifier = Modifier.width(120.dp))
                            Text(
                                text = "Foto Profil",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontFamily = poppinsFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(22.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Opsi Kamera
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    // Tambahkan aksi untuk membuka kamera
                                    showPopup = false
                                    showBottomNav = true
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.camera_2),
                                    contentDescription = "Kamera",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .border(
                                            width = 2.dp,
                                            color = colorResource(id = R.color.gray_icon),
                                            shape = CircleShape
                                        )
                                        .padding(8.dp),
                                    tint = colorResource(id = R.color.green_logo)
                                )
                                Text(
                                    text = "Kamera",
                                    fontSize = 12.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                            }
                            // Opsi Galeri
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    // Tambahkan aksi untuk membuka galeri
                                    showPopup = false
                                    showBottomNav = true
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.photograph),
                                    contentDescription = "Galeri",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .border(
                                            width = 2.dp,
                                            color = colorResource(id = R.color.gray_icon),
                                            shape = CircleShape
                                        )
                                        .padding(8.dp),

                                    tint = colorResource(id = R.color.green_logo)
                                )
                                Text(
                                    text = "Galeri",
                                    fontSize = 12.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                            }
                            // Opsi Hapus
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    // Tambahkan aksi untuk menghapus foto
                                    showPopup = false
                                    showBottomNav = true
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.trash),
                                    contentDescription = "Hapus",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .border(
                                            width = 2.dp,
                                            color = colorResource(id = R.color.gray_icon),
                                            shape = CircleShape
                                        )
                                        .padding(8.dp),
                                    tint = colorResource(id = R.color.green_logo)
                                )
                                Text(
                                    text = "Hapus",
                                    fontSize = 12.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HalamanEditProfilePreview() {
    EdufarmTheme {
        HalamanEditProfile(
            navController = rememberNavController(),
            modifier = Modifier
        )
    }
}