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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.ErrorMessages
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.LupaPasswordState
import com.example.edufarm.viewModel.LupaPasswordViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AturUlangSandiScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    email: String,
    otp: String
) {
    val katasandibaruText = remember { mutableStateOf("") }
    var katasandibaruVisible by remember { mutableStateOf(false) }
    val konfirmasiKatasandibaruText = remember { mutableStateOf("") }
    var konfirmasiKatasandibaruVisible by remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val successMessage = remember { mutableStateOf("") }
    val errors = remember { mutableStateListOf<String>() }
    val viewModel: LupaPasswordViewModel = viewModel(
        factory = LupaPasswordViewModelFactory(
            AuthRepository(ApiClient.apiService)
        )
    )
    val resetPasswordState by viewModel.resetState.collectAsState()

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    LaunchedEffect(resetPasswordState) {
        when (resetPasswordState) {
            is LupaPasswordState.Success -> {
                successMessage.value = (resetPasswordState as LupaPasswordState.Success).message
                navController.navigate(Routes.NOTIFIKASI_PASSWORD)
            }
            is LupaPasswordState.Error -> {
                errorMessage.value = (resetPasswordState as LupaPasswordState.Error).message
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
                    // Teks Edu Farm
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
                        text = "Atur ulang kata sandi dengan memasukkan kata sandi yang baru",
                        fontFamily = poppinsFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                ErrorMessages(errors = errors)
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // Kata Sandi Baru
                    Text(
                        text = "Kata Sandi Baru",
                        fontFamily = poppinsFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BasicTextField(
                        value = katasandibaruText.value,
                        onValueChange = { katasandibaruText.value = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (!katasandibaruVisible) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(
                                1.dp,
                                colorResource(id = R.color.green_logo),
                                RoundedCornerShape(15.dp)
                            )
                            .padding(horizontal = 21.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (katasandibaruText.value.isEmpty()) {
                                            Text(
                                                "Masukan Kata Sandi",
                                                color = Color.Gray,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = poppinsFontFamily
                                            )
                                        }
                                        innerTextField()
                                    }

                                    IconButton(
                                        onClick = { katasandibaruVisible = !katasandibaruVisible },
                                        modifier = Modifier.size(48.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(
                                                id = if (katasandibaruVisible) {
                                                    R.drawable.mdi_eye_outline
                                                } else {
                                                    R.drawable.mdi_hide_outline
                                                }
                                            ),
                                            contentDescription = if (katasandibaruVisible) {
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

                    Spacer(modifier = Modifier.height(10.dp))

                    // Konfirmasi Kata Sandi Baru
                    Text(
                        text = "Konfirmasi Kata Sandi Baru",
                        fontFamily = poppinsFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BasicTextField(
                        value = konfirmasiKatasandibaruText.value,
                        onValueChange = { konfirmasiKatasandibaruText.value = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (!konfirmasiKatasandibaruVisible) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(
                                1.dp,
                                colorResource(id = R.color.green_logo),
                                RoundedCornerShape(15.dp)
                            )
                            .padding(horizontal = 21.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (konfirmasiKatasandibaruText.value.isEmpty()) {
                                            Text(
                                                "Masukan Kata Sandi",
                                                color = Color.Gray,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = poppinsFontFamily
                                            )
                                        }
                                        innerTextField()
                                    }

                                    IconButton(
                                        onClick = {
                                            konfirmasiKatasandibaruVisible =
                                                !konfirmasiKatasandibaruVisible
                                        },
                                        modifier = Modifier.size(48.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(
                                                id = if (konfirmasiKatasandibaruVisible) {
                                                    R.drawable.mdi_eye_outline
                                                } else {
                                                    R.drawable.mdi_hide_outline
                                                }
                                            ),
                                            contentDescription = if (konfirmasiKatasandibaruVisible) {
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

                    Spacer(modifier = Modifier.height(60.dp))

                    Button(
                        onClick = {
                            errors.clear() // Bersihkan error sebelumnya

                            if (katasandibaruText.value.isEmpty()) {
                                errors.add("Kata sandi baru tidak boleh kosong.")
                            }
                            if (konfirmasiKatasandibaruText.value.isEmpty()) {
                                errors.add("Konfirmasi kata sandi baru tidak boleh kosong.")
                            }
                            if (katasandibaruText.value != konfirmasiKatasandibaruText.value) {
                                errors.add("Kata sandi baru dan konfirmasi kata sandi tidak cocok.")
                            }

                            if (errors.isEmpty()) {
                                viewModel.resetPassword(
                                    email = email,
                                    otp = otp,
                                    passwordBaru = katasandibaruText.value,
                                    konfirmasiPassword = konfirmasiKatasandibaruText.value
                                )
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
fun AturUlangSandiScreenPreview() {
    EdufarmTheme {
        AturUlangSandiScreen(
            navController = rememberNavController(),
            modifier = Modifier,
            email = "",
            otp = ""
        )
    }
}