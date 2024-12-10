package com.example.edufarm

import android.app.Application
import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.dataStore.saveToken
import com.example.edufarm.data.repository.AuthRepository
import com.example.edufarm.factory.LoginViewModelFactory
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.LoginState
import com.example.edufarm.viewModel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current.applicationContext as Application
    val repository = AuthRepository(ApiClient.apiService)
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(repository, context)
    )

    val emailText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val loginState by viewModel.loginState.collectAsState()

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Edu Farm Logo",
            modifier = Modifier
                .size(220.dp)
                .padding(top = 26.dp, bottom = 35.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Input Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .shadow(10.dp, RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                .background(Color.White, RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Title
                TitleRow()

                // Email Input
                InputField(
                    text = emailText.value,
                    onTextChange = { emailText.value = it },
                    placeholder = "Masukan Email",
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(21.dp))

                // Password Input
                InputField(
                    text = passwordText.value,
                    onTextChange = { passwordText.value = it },
                    placeholder = "Masukan Password",
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = it }
                )

                Spacer(modifier = Modifier.height(5.dp))

                // Forgot Password
                ForgotPasswordText(navController)

                Spacer(modifier = Modifier.height(20.dp))

                // Login Button
                LoginButton(
                    onClick = {
                        if (emailText.value.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(emailText.value).matches()) {
                            Toast.makeText(context, "Masukkan email yang valid", Toast.LENGTH_SHORT).show()
                            return@LoginButton
                        }
                        if (passwordText.value.isBlank()) {
                            Toast.makeText(context, "Masukkan password", Toast.LENGTH_SHORT).show()
                            return@LoginButton
                        }
                        viewModel.login(emailText.value, passwordText.value)
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Sign-up Link
                SignUpRow(navController)

                Spacer(modifier = Modifier.height(26.dp))

                // Or Separator
                OrSeparator()

                Spacer(modifier = Modifier.height(19.dp))

                // Social Media Login
                SocialMediaLogin()

                Spacer(modifier = Modifier.height(20.dp))

                when (loginState) {
                    is LoginState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is LoginState.Success -> {
                        val token = (loginState as LoginState.Success).token
                        LaunchedEffect(token) {
                            saveToken(context, emailText.value, token) // Gunakan email input
                            navController.navigate(Routes.HALAMAN_BERANDA) {
                                popUpTo(Routes.HALAMAN_LOGIN) { inclusive = true }
                            }
                        }
                    }
                    is LoginState.Error -> {
                        val errorMessage = (loginState as LoginState.Error).message
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}



@Composable
fun TitleRow() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Edu",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily,
            color = colorResource(id = R.color.green_edu),
            modifier = Modifier.padding(bottom = 43.dp)
        )
        Text(
            text = "Farm",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily,
            color = colorResource(id = R.color.green_logo),
            modifier = Modifier.padding(bottom = 43.dp)
        )
    }
}

@Composable
fun InputField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChange: (Boolean) -> Unit = {}
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, colorResource(id = R.color.green_logo), RoundedCornerShape(15.dp))
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
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily
                    )
                }
                innerTextField()
                if (isPassword) {
                    IconButton(
                        onClick = { onPasswordVisibilityChange(!passwordVisible) },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(36.dp) // Menambahkan ukuran eksplisit untuk ikon
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.mdi_eye_outline else R.drawable.mdi_hide_outline
                            ),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp) // Ukuran ikon mata
                        )
                    }
                }
            }
        }
    )
}



@Composable
fun ForgotPasswordText(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Lupa Kata Sandi?",
            color = colorResource(id = R.color.green_logo),
            fontSize = 12.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                navController.navigate(Routes.LUPA_PASSWORD)
            }
        )
    }
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
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
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
fun SignUpRow(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Belum Memiliki Akun ?",
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Daftar disini",
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.green_logo),
            modifier = Modifier.clickable {
                navController.navigate(Routes.HALAMAN_DAFTAR)
            }
        )
    }
}

@Composable
fun OrSeparator() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Gray)
        )
        Text(
            text = " atau masuk dengan ",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}

@Composable
fun SocialMediaLogin() {
    IconButton(onClick = { /* Handle Google login */ }) {
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "Google",
            modifier = Modifier.size(22.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    EdufarmTheme {
        LoginScreen(
            navController = rememberNavController())
    }
}
