package com.example.edufarm

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LoginScreen(navController: NavController,modifier: Modifier = Modifier) {

    val emailText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Edu Farm Logo",
            modifier = Modifier
                .size(220.dp)
                .padding(top = 26.dp)
                .padding(bottom = 35.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .shadow(10.dp, RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                .background(Color.White, RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edu",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily,
                        color = colorResource(id = R.color.green_edu),
                        modifier = Modifier
                            .padding(bottom = 43.dp)
                    )
                    Text(
                        text = "Farm",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily,
                        color = colorResource(id = R.color.green_logo),
                        modifier = Modifier
                            .padding(bottom = 43.dp)
                    )
                }
                // Email Field
                BasicTextField(
                    value = emailText.value,
                    onValueChange = { emailText.value = it},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email, // Menampilkan keyboard tipe email
                        imeAction = ImeAction.Next // Tombol Next untuk pindah ke field berikutnya
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, colorResource(id = R.color.green_logo), RoundedCornerShape(15.dp))
                        .padding(horizontal = 21.dp),

                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (emailText.value.isEmpty()) {
                                Text(
                                    "Masukan Email",
                                    color = Color.Gray,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = poppinsFontFamily,
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                )

                            }
                            innerTextField()
                        }
                    }
                )


                Spacer(modifier = Modifier.height(21.dp))

                // Password Field
                BasicTextField(
                    value = passwordText.value,
                    onValueChange = {passwordText.value = it},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, // Menampilkan keyboard tipe password
                        imeAction = ImeAction.Done // Tombol Done untuk menyelesaikan input
                    ),
                    visualTransformation = if (!passwordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, colorResource(id = R.color.green_logo), RoundedCornerShape(15.dp))
                        .padding(horizontal = 21.dp),

                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (passwordText.value.isEmpty()) {  // Tampilkan placeholder jika teks kosong
                                        Text(
                                            "Masukan Password",
                                            color = Color.Gray,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = poppinsFontFamily
                                        )
                                    }
                                    innerTextField()
                                }


                                IconButton(
                                    onClick = { passwordVisible = !passwordVisible },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (passwordVisible) {
                                                R.drawable.mdi_eye_outline
                                            } else {
                                                R.drawable.mdi_hide_outline
                                            }
                                        ),
                                        contentDescription = if (passwordVisible) {
                                            "Hide Password"
                                        } else {
                                            "Show Password"
                                        },
                                        tint = Color.Gray,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))
                // "Lupa Kata Sandi" Text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End // Menempatkan teks di kanan bawah
                ) {
                    Text(
                        text = "Lupa Kata Sandi?",
                        color = colorResource(id = R.color.green_logo),
                        fontSize = 12.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.clickable {
//                            navController.navigate(Routes.HALAMAN_LUPA_SANDI) // Navigasi ke halaman reset password
//                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Login Button
                Button(
                    onClick = { navController.navigate(Routes.HALAMAN_BERANDA) },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "Masuk",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Sign-up Link
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Belum Memiliki Akun ?",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Daftar disini",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.green_logo),
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.HALAMAN_DAFTAR) // Navigate to Daftar
                        }
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                // Or Separator
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray))
                    Text(
                        text = " atau masuk dengan ",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Box(modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray))
                }

                Spacer(modifier = Modifier.height(19.dp))

                // Social Media Login
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /* handle Google login */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = "Google",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    EdufarmTheme {
        LoginScreen(
            navController = rememberNavController(),
            modifier = Modifier)
    }
}