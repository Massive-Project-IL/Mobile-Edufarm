package com.example.edufarm.akun.password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.R
import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.repository.AuthRepository
import com.example.edufarm.factory.LupaPasswordViewModelFactory
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.LupaPasswordState
import com.example.edufarm.viewModel.LupaPasswordViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LupaPasswordScreen(
    navController: NavController,
    viewModel: LupaPasswordViewModel = viewModel(
        factory = LupaPasswordViewModelFactory(
            AuthRepository(ApiClient.apiService)
        )
    ),
    modifier: Modifier = Modifier
) {
    val emailText = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }
    val successMessage = remember { mutableStateOf("") }
    val lupaPasswordState by viewModel.otpState.collectAsState()

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(color = topBarColor, darkIcons = true)
    }

    // Respons Backend
    LaunchedEffect(lupaPasswordState) {
        when (lupaPasswordState) {
            is LupaPasswordState.Success -> {
                successMessage.value = (lupaPasswordState as LupaPasswordState.Success).message
                // Navigasi ke halaman verifikasi jika OTP berhasil dikirim
                navController.navigate("verifikasi_email_screen/${emailText.value}")
            }

            is LupaPasswordState.Error -> {
                errorMessage.value = "Gagal mengirim OTP. Silakan coba lagi nanti."
            }

            else -> {}
        }
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
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Edu",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.green_edu)
                        )
                        Text(
                            text = "Farm",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.green_logo)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Untuk mengatur ulang kata sandi, masukkan E-mail kamu yang sudah terdaftar akun EduFarm kamu",
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    BasicTextField(
                        value = emailText.value,
                        onValueChange = { emailText.value = it },
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
                                if (emailText.value.isEmpty()) {
                                    Text(
                                        "Masukan E-mail",
                                        color = Color.Gray,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = poppinsFontFamily
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Menampilkan Pesan Error jika ada
                    if (errorMessage.value.isNotEmpty()) {
                        Text(
                            text = errorMessage.value,
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Menampilkan Pesan Sukses jika ada
                    if (successMessage.value.isNotEmpty()) {
                        Text(
                            text = successMessage.value,
                            color = Color.Green,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(90.dp))

                    Button(
                        onClick = {
                            if (emailText.value.isNotEmpty()) {
                                errorMessage.value = ""
                                successMessage.value = ""
                                // Panggil ViewModel untuk mengirim OTP
                                viewModel.kirimOtp(emailText.value)
                            } else {
                                errorMessage.value = "Email tidak boleh kosong."
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Text(
                            text = "Kirim Kode Verifikasi",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LupaPasswordScreenPreview() {
    EdufarmTheme {
        LupaPasswordScreen(
            navController = rememberNavController(),
            modifier = Modifier
        )
    }
}