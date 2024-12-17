package com.example.edufarm

import android.app.Application
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.repository.AuthRepository
import com.example.edufarm.factory.LoginViewModelFactory
import com.example.edufarm.factory.RegisterViewModelFactory
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.ErrorMessages
import com.example.edufarm.ui.components.OrSeparator
import com.example.edufarm.ui.components.SocialMediaLogin
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.LoginState
import com.example.edufarm.viewModel.LoginViewModel
import com.example.edufarm.viewModel.RegisterState
import com.example.edufarm.viewModel.RegisterViewModel

@Composable
fun DaftarScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val repository = AuthRepository(ApiClient.apiService)
    val factory = RegisterViewModelFactory(repository)
    val viewModel: RegisterViewModel = viewModel(factory = factory)

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(
            AuthRepository(ApiClient.apiService),
            LocalContext.current.applicationContext as Application
        )
    )

    // Input field states
    val namaText = remember { mutableStateOf("") }
    val emailText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    val telponText = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val errorMessages = remember { mutableStateOf(listOf<String>()) }
    val registerState by viewModel.registerState.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()

    fun validateForm(): Boolean {
        val errors = mutableListOf<String>()
        if (namaText.value.isEmpty()) errors.add("Nama lengkap harus diisi.")
        if (emailText.value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText.value).matches())
            errors.add("Email harus valid.")
        if (passwordText.value.isEmpty() || passwordText.value.length < 8)
            errors.add("Password minimal 8 karakter.")
        if (telponText.value.isEmpty() || !telponText.value.all { it.isDigit() } || telponText.value.length < 10)
            errors.add("Nomor HP harus berupa angka minimal 10 digit.")
        errorMessages.value = errors
        return errors.isEmpty()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Daftar", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 26.dp))
        Spacer(modifier = Modifier.height(28.dp))

        if (errorMessages.value.isNotEmpty()) {
            ErrorMessages(errors = errorMessages.value)
            Spacer(modifier = Modifier.height(16.dp))
        }

        InputField(
            text = namaText.value,
            onTextChange = { namaText.value = it },
            placeholder = "Nama Lengkap",
            isError = errorMessages.value.contains("Nama lengkap harus diisi.")
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            text = emailText.value,
            onTextChange = { emailText.value = it },
            placeholder = "Alamat Email",
            isError = errorMessages.value.contains("Email harus valid.")
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            text = passwordText.value,
            onTextChange = { passwordText.value = it },
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = it },
            isError = errorMessages.value.contains("Password minimal 8 karakter.")
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            text = telponText.value,
            onTextChange = { telponText.value = it },
            placeholder = "Nomor Telepon",
            isError = errorMessages.value.contains("Nomor HP harus berupa angka minimal 10 digit.")
        )
        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                if (validateForm()) {
                    viewModel.registerUser(
                        email = emailText.value,
                        password = passwordText.value,
                        nama = namaText.value,
                        telpon = telponText.value
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Daftar", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sudah Memiliki Akun ?",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Masuk",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.green_logo),
                modifier = Modifier.clickable {
                    navController.navigate(Routes.HALAMAN_LOGIN)
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        OrSeparator("atau daftar dengan")
        Spacer(modifier = Modifier.height(16.dp))

        SocialMediaLogin(viewModel = loginViewModel, navController = navController)

        when (registerState) {
            is RegisterState.Loading -> CircularProgressIndicator()
            is RegisterState.Success -> navController.navigate(Routes.HALAMAN_NOTIFIKASI_DAFTAR)
            is RegisterState.Error -> errorMessages.value = listOf((registerState as RegisterState.Error).message)
            else -> Unit
        }

        when (loginState) {
            is LoginState.Loading -> CircularProgressIndicator()
            is LoginState.Success -> {
                navController.navigate(Routes.HALAMAN_BERANDA) {
                    popUpTo(0)
                }
            }
            is LoginState.Error -> errorMessages.value = listOf((loginState as LoginState.Error).message)
            else -> Unit
        }
    }
}


@Composable
private fun InputField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, if (isError) Color.Red else colorResource(id = R.color.green), RoundedCornerShape(15.dp))
            .background(if (isError) Color(0x1AFF0000) else Color.Transparent)
            .padding(horizontal = 21.dp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
private fun PasswordField(
    text: String,
    onTextChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    isError: Boolean = false
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, if (isError) Color.Red else colorResource(id = R.color.green), RoundedCornerShape(15.dp)) // Garis luar merah jika error, hijau jika valid
            .background(if (isError) Color(0x1AFF0000) else Color.Transparent) // Background merah jika error
            .padding(horizontal = 21.dp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = "Masukkan Password",
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily
                    )
                }
                innerTextField()
                IconButton(
                    onClick = { onPasswordVisibilityChange(!passwordVisible) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.mdi_eye_outline else R.drawable.mdi_hide_outline
                        ),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DaftarScreenPreview() {
    EdufarmTheme {
        DaftarScreen(
            navController = rememberNavController(),
            modifier = Modifier)
    }
}