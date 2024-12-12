package com.example.edufarm.akun

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.edufarm.ErrorMessages
import com.example.edufarm.R
import com.example.edufarm.data.model.PasswordUpdateRequest
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.PenggunaViewModel
import com.example.edufarm.viewModel.UpdatePasswordState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun UbahSandiScreen(
    navController: NavController,
    penggunaViewModel: PenggunaViewModel
) {
    val selectedItem = remember { mutableStateOf("Akun") }
    var showDialog by remember { mutableStateOf(false) }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val updatePasswordState by penggunaViewModel.updatePasswordState.collectAsState()
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)
    val errorMessages = remember { mutableStateOf(listOf<String>()) } // To display errors locally

    // Set status bar color
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    Scaffold(
        modifier = Modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(R.color.background))
                .padding(start = 35.dp, end = 35.dp, top = 5.dp)
        ) {
            TopBar(
                navController = navController,
                title = "Privasi dan Keamanan"
            )

            Spacer(modifier = Modifier.height(41.dp))

            // Display error messages
            if (errorMessages.value.isNotEmpty()) {
                ErrorMessages(errors = errorMessages.value)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Input for Old Password
            Text(
                text = "Kata Sandi Lama",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 16.sp,
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            SandiField(value = oldPassword, onValueChange = { oldPassword = it })

            Spacer(modifier = Modifier.height(16.dp))

            // Input for New Password
            Text(
                text = "Kata Sandi Baru",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            SandiField(value = newPassword, onValueChange = { newPassword = it })

            Spacer(modifier = Modifier.height(16.dp))

            // Input for Confirm Password
            Text(
                text = "Konfirmasi Kata Sandi Baru",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            SandiField(value = confirmPassword, onValueChange = { confirmPassword = it })

            Spacer(modifier = Modifier.height(60.dp))

            // Save Changes Button
            Button(
                onClick = {
                    // Validate input locally
                    val errors = mutableListOf<String>()
                    if (oldPassword.isEmpty()) errors.add("Kata sandi lama harus diisi.")
                    if (newPassword.length < 8) errors.add("Kata sandi baru harus minimal 8 karakter.")
                    if (newPassword != confirmPassword) errors.add("Konfirmasi kata sandi tidak sesuai.")
                    if (errors.isNotEmpty()) {
                        errorMessages.value = errors // Display errors
                    } else {
                        showDialog = true // Show confirmation dialog
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "Simpan Perubahan",
                    color = colorResource(R.color.white),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily
                )
            }

            // Handle Update Password State from ViewModel
            when (updatePasswordState) {
                is UpdatePasswordState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is UpdatePasswordState.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(Routes.NOTIFIKASI_SANDI) {
                            popUpTo(Routes.HALAMAN_UBAH_SANDI) { inclusive = true }
                        }
                    }
                }
                is UpdatePasswordState.Error -> {
                    errorMessages.value = listOf((updatePasswordState as UpdatePasswordState.Error).message)
                }
                else -> {}
            }
        }

        // Confirmation Dialog
        if (showDialog) {
            ConfirmationDialog(
                message = "Apakah Kamu Yakin Ingin Mengubahnya?",
                onDismissRequest = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    penggunaViewModel.updatePassword(
                        PasswordUpdateRequest(
                            email_user = penggunaViewModel.getEmailUser().orEmpty(), // Get email from ViewModel
                            oldPassword = oldPassword,
                            newPassword = newPassword,
                            confirmPassword = confirmPassword
                        )
                    )
                },
                onCancel = { showDialog = false }
            )
        }
    }
}

@Composable
private fun SandiField(value: String, onValueChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (!passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.white))
            .height(45.dp)
            .border(1.dp, colorResource(id = R.color.green), RoundedCornerShape(10.dp))
            .padding(horizontal = 20.dp),

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
                        if (value.isEmpty()) {
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
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier
                            .size(48.dp)
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
}

//
//@Preview(showBackground = true)
//@Composable
//fun PreviewUbahSandiScreen() {
//    EdufarmTheme {
//        UbahSandiScreen(navController = rememberNavController(),
//            penggunaViewModel = PenggunaViewModel()
//        )
//    }
//}

