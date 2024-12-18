package com.example.edufarm.akun

import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.edufarm.R
import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.dataStore.clearToken
import com.example.edufarm.data.dataStore.getCurrentUserEmail
import com.example.edufarm.data.model.Pengguna
import com.example.edufarm.data.repository.PenggunaRepository
import com.example.edufarm.factory.PenggunaViewModelFactory
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.PenggunaViewModel
import com.example.edufarm.viewModel.ProfileState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: PenggunaViewModel = viewModel(
        factory = PenggunaViewModelFactory(
            PenggunaRepository(ApiClient.apiService),
            LocalContext.current.applicationContext as Application
        )
    )
) {
    var showDialog by remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf("Akun") }
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)
    val profileState by viewModel.penggunaState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPengguna()
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    Scaffold(
        modifier = Modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }
    ) { paddingValues ->
        when (profileState) {
            is ProfileState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ProfileState.Success -> {
                val profile = (profileState as ProfileState.Success).pengguna
                ProfileContent(
                    profile = profile,
                    navController = navController,
                    paddingValues = paddingValues,
                    showDialog = showDialog,
                    onDialogChange = { showDialog = it }
                )
            }

            is ProfileState.Error -> {
                val errorMessage = (profileState as ProfileState.Error).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileContent(
    profile: Pengguna,
    navController: NavController,
    paddingValues: PaddingValues,
    showDialog: Boolean,
    onDialogChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 37.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Akun",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        )
        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .size(110.dp)
                .background(
                    color = if (profile.foto_profile?.contains("null") == true) Color(0xFF92D5B6) else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (profile.foto_profile.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(color = Color(0xFF92D5B6), shape = CircleShape), // Background Hijau
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.default_image),
                        contentDescription = "Default Profile",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    )
                }
            } else {
                Image(
                    painter = rememberAsyncImagePainter(model = profile.foto_profile),
                    contentDescription = "Foto Profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
            }

        }

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = profile.nama_user ?: "Nama Tidak Tersedia",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = profile.email_user ?: "Email Tidak Tersedia",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = poppinsFontFamily
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = profile.telpon_user ?: "Nomor Telepon Tidak Tersedia",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = poppinsFontFamily
        )

        Spacer(modifier = Modifier.height(10.dp))
        if (profile.is_default_password) {
            Text(
                text = "Peringatan: Anda masih menggunakan sandi bawaan. Ubah sandi Anda untuk melindungi akun.",
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.pembatas),
            contentDescription = "Pembatas",
            modifier = Modifier
                .fillMaxWidth()
                .size(2.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Profil",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        )

        Spacer(modifier = Modifier.height(12.dp))
        ProfileRowItem(
            iconId = R.drawable.ic_edit,
            text = "Edit",
            onClick = { navController.navigate(Routes.HALAMAN_EDIT_PROFILE) }
        )

        Spacer(modifier = Modifier.height(21.dp))
        // Privasi & Keamanan
        ProfileRowItem(
            iconId = R.drawable.ic_keamanan,
            text = "Privasi & Keamanan",
            onClick = { navController.navigate(Routes.HALAMAN_UBAH_SANDI) }
        )

        Spacer(modifier = Modifier.height(21.dp))
        // Pengaturan
        Text(
            text = "Pengaturan",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Tentang Kami
        ProfileRowItem(
            iconId = R.drawable.ic_aboutus,
            text = "Tentang Kami",
            onClick = { navController.navigate(Routes.HALAMAN_TENTANG_KAMI) }
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Keluar
        ProfileRowItem(
            iconId = R.drawable.logout,
            text = "Keluar",
            onClick = { onDialogChange(true) }
        )
        if (showDialog) {
            ConfirmationKeluar(
                message = "Apakah Kamu Yakin Ingin Keluar?",
                onDismissRequest = { onDialogChange(false) },
                onConfirm = {
                    onDialogChange(false)
                    navController.navigate(Routes.HALAMAN_LOGIN) {
                        popUpTo(0)
                    }
                },
                onCancel = { onDialogChange(false) }
            )
        }
    }
}

@Composable
fun ProfileRowItem(iconId: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = text,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            color = colorResource(id = R.color.black)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.row),
            contentDescription = "Arrow",
            modifier = Modifier.size(23.dp)
        )
    }
}

@Composable
private fun ConfirmationKeluar(
    message: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(16.dp),
        containerColor = colorResource(R.color.background_confirm),
        properties = DialogProperties(
            dismissOnClickOutside = false
        ),
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.keluar),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(165.dp)
                        .aspectRatio(1f)
                )
                Text(
                    text = message,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp,
                    letterSpacing = (-0.24).sp,
                    color = colorResource(R.color.black)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onCancel,
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(1.dp, colorResource(R.color.green_button)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.white)
                        ),
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp),
                        modifier = Modifier
                            .width(90.dp)
                            .height(35.dp)
                            .padding(end = 11.5.dp)
                    ) {
                        Text(
                            text = "Batal",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily,
                            lineHeight = 20.sp,
                            letterSpacing = (-0.24).sp,
                            color = colorResource(R.color.black)
                        )
                    }

                    Button(
                        onClick = {
                            onConfirm()
                            CoroutineScope(Dispatchers.IO).launch {
                                val email = getCurrentUserEmail(context)
                                if (!email.isNullOrEmpty()) {
                                    clearToken(context, email)
                                    Log.d("ClearToken", "Token untuk $email berhasil dihapus.")
                                } else {
                                    Log.e("ClearToken", "Email pengguna aktif tidak ditemukan.")
                                }
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green_button)),
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp),
                        modifier = Modifier
                            .width(90.dp)
                            .height(35.dp)
                            .padding(start = 11.5.dp)
                    ) {
                        Text(
                            text = "Ya",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily,
                            lineHeight = 20.sp,
                            letterSpacing = (-0.24).sp,
                            color = colorResource(R.color.white)
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    EdufarmTheme {
        ProfileScreen(
            navController = rememberNavController()
        )
    }
}