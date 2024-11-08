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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun LoginScreen(navController: NavController,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.baru_2),
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
                        color = colorResource(id = R.color.orange_text),
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
                    value = "", // Add state to handle input text
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, colorResource(id = R.color.green_logo), RoundedCornerShape(15.dp))
                        .padding(horizontal = 21.dp),

                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (true) { // Replace with condition to check if field is empty
                                Text(
                                    "Masukan Email",
                                    color = Color.Gray,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = poppinsFontFamily,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(21.dp))

                // Password Field
                BasicTextField(
                    value = "", // Add state to handle input text
                    onValueChange = {},
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, colorResource(id = R.color.green_logo), RoundedCornerShape(15.dp))
                        .padding(horizontal = 21.dp),

                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .fillMaxWidth()
                            ) {
                                if (true) { // Replace with condition to check if field is empty
                                    Text(
                                        "Masukan Password",
                                        color = Color.Gray,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = poppinsFontFamily,
                                        modifier = Modifier
                                            .weight(1f)
                                    )
                                }
                                IconButton(onClick = { /* Handle visibility toggle */ }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.mdi_hide_outline),
                                        contentDescription = "Hide Password",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(37.dp))

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
                    Spacer(modifier = Modifier.width(51.dp))
                    IconButton(onClick = { /* handle Facebook login */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "Facebook",
                            modifier = Modifier.size(31.dp)
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
