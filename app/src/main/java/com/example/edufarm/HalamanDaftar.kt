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
fun DaftarScreen(navController: NavController,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Daftar",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 26.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Lengkapi data dirimu",
            fontSize = 16.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Nama Lengkap Field
        InputField(placeholder = "Nama Lengkap")

        Spacer(modifier = Modifier.height(25.dp))

        // Alamat Email Field
        InputField(placeholder = "Alamat Email")

        Spacer(modifier = Modifier.height(25.dp))

        // Password Field with Eye Icon
        PasswordField()

        Spacer(modifier = Modifier.height(25.dp))

        // No HP Field
        InputField(placeholder = "No. Hp")

        Spacer(modifier = Modifier.height(25.dp))

        // Daftar Button
        Button(
            onClick = { navController.navigate(Routes.HALAMAN_NOTIFIKASI_DAFTAR) },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Daftar",
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Login Link
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sudah Memiliki Akun ?",
                fontSize = 15.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Masuk",
                fontSize = 15.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.green_logo),
                modifier = Modifier.clickable {
                    navController.navigate(Routes.HALAMAN_LOGIN) // Navigate to Daftar
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Or Separator
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Gray))
            Text(
                text = " atau daftar dengan ",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 11.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Box(modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Gray))
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            Spacer(modifier = Modifier.width(48.dp))
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

@Composable
fun InputField(placeholder: String) {
    BasicTextField(
        value = "", // Add state to handle input text
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, colorResource(id = R.color.green_logo), RoundedCornerShape(15.dp))
            .padding(horizontal = 16.dp),
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (true) { // Replace with condition to check if field is empty
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun PasswordField() {
    BasicTextField(
        value = "", // Add state to handle input text
        onValueChange = {},
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, colorResource(id = R.color.green_logo), RoundedCornerShape(15.dp))
            .padding(horizontal = 16.dp),
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
                            text = "Password",
                            color = Color.Gray,
                            fontSize = 15.sp,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier.weight(1f)
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