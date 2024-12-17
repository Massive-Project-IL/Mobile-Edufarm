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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
fun VerifikasiEmailScreen(
    navController: NavController,
    email: String,
    viewModel: LupaPasswordViewModel = viewModel(factory = LupaPasswordViewModelFactory(
        AuthRepository(ApiClient.apiService)
    )),
    modifier: Modifier = Modifier
) {
    val kodeLength = 4
    var kodeVerifikasi by remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }
    val successMessage = remember { mutableStateOf("") }
    val resetState by viewModel.resetState.collectAsState()
    val focusRequesters = List(kodeLength) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Masking email untuk privasi
    val maskedEmail = email.replaceBefore("@", "****")

    val errors = remember { mutableStateListOf<String>() }

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    // Respons Backend untuk verifikasi OTP
    LaunchedEffect(resetState) {
        when (resetState) {
            is LupaPasswordState.Success -> {
                successMessage.value = (resetState as LupaPasswordState.Success).message
                // Navigasi ke halaman Atur Ulang Sandi dengan membawa email dan OTP
                navController.navigate("halamanAturUlangSandi?email=${email}&otp=${kodeVerifikasi}")
            }
            is LupaPasswordState.Error -> {
                errors.clear() // Bersihkan error sebelumnya
                errors.add("Kode OTP yang kamu masukkan salah. Silakan periksa kembali.")
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
                .padding(16.dp)
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
                        text = "Masukkan kode Verifikasi yang telah dikirim ke E-mail kamu",
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = "Verifikasi untuk email: $maskedEmail",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))
                // Input Kode OTP
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        (0 until kodeLength).forEach { index ->
                            val isFocused = kodeVerifikasi.length == index
                            BasicTextField(
                                value = if (index < kodeVerifikasi.length) kodeVerifikasi[index].toString() else "",
                                onValueChange = { value ->
                                    if (value.length <= 1 && value.all { it.isDigit() }) {
                                        kodeVerifikasi = kodeVerifikasi
                                            .take(index) + value + kodeVerifikasi.drop(index + 1)

                                        // Pindahkan fokus ke kotak berikutnya
                                        if (value.isNotEmpty() && index < kodeLength - 1) {
                                            focusRequesters[index + 1].requestFocus()
                                        } else if (index == kodeLength - 1) {
                                            // Jika sudah di kotak terakhir, tutup keyboard
                                            focusManager.clearFocus()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .size(45.dp)
                                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                                    .border(
                                        width = if (isFocused) 2.dp else 1.dp,
                                        color = if (isFocused) colorResource(id = R.color.green_logo) else Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .focusRequester(focusRequesters[index])
                                    .padding(8.dp),
                                textStyle = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontFamily = poppinsFontFamily,
                                    textAlign = TextAlign.Center
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                singleLine = true
                            )
                        }
                    }

                    // Pesan Error
                    if (errorMessage.value.isNotEmpty()) {
                        Text(
                            text = errorMessage.value,
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(90.dp))

                    // Tombol Konfirmasi
                    Button(
                        onClick = {
                            if (kodeVerifikasi.length == kodeLength) {
                                // Panggil viewModel untuk verifikasi OTP
                                viewModel.verifikasiOtp(email, kodeVerifikasi)
                            } else {
                                errorMessage.value = "Kode verifikasi harus 4 digit."
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Text(
                            text = "Konfirmasi",
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
fun VerifikasiEmailScreenPreview() {
    EdufarmTheme {
        VerifikasiEmailScreen(
            navController = rememberNavController(),
            modifier = Modifier,
            email = ""
        )
    }
}