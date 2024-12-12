package com.example.edufarm.akun

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.edufarm.R
import com.example.edufarm.data.model.Pengguna
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.EditProfileState
import com.example.edufarm.viewModel.PenggunaViewModel
import com.example.edufarm.viewModel.ProfileState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HalamanEditProfile(
    navController: NavController,
    penggunaViewModel: PenggunaViewModel,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }
    var showBottomNav by remember { mutableStateOf(true) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val editState by penggunaViewModel.editPenggunaState.collectAsState()
    val penggunaState by penggunaViewModel.penggunaState.collectAsState()

    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.green)

    // Set status bar color
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
        penggunaViewModel.getPengguna() // Fetch user data on screen load
    }

    // Update state when penggunaState changes
    LaunchedEffect(penggunaState) {
        if (penggunaState is ProfileState.Success) {
            val pengguna = (penggunaState as ProfileState.Success).pengguna
            name = pengguna.nama_user ?: ""
            email = pengguna.email_user ?: ""
            phoneNumber = pengguna.telpon_user ?: ""
        }
    }

    LaunchedEffect(editState) {
        when (editState) {
            is EditProfileState.Success -> {
                val pengguna = (editState as EditProfileState.Success).pengguna
                if (pengguna != null) {
                    successMessage = "Profil berhasil diperbarui!"
                    errorMessage = null
                    // Navigasi ke halaman notifikasi profile
                    navController.navigate(Routes.NOTIFIKASI_PROFILE) {
                        popUpTo(Routes.HALAMAN_EDIT_PROFILE) { inclusive = true }
                    }
                } else {
                    errorMessage = "Profil berhasil diperbarui, tetapi data pengguna tidak diperbarui dari server."
                    successMessage = null
                }
            }
            is EditProfileState.SuccessMessage -> {
                successMessage = (editState as EditProfileState.SuccessMessage).message
                errorMessage = null
                // Navigasi ke halaman notifikasi profile
                navController.navigate(Routes.NOTIFIKASI_PROFILE) {
                    popUpTo(Routes.HALAMAN_EDIT_PROFILE) { inclusive = true }
                }
            }
            is EditProfileState.Error -> {
                errorMessage = (editState as EditProfileState.Error).message
                successMessage = null
            }
            else -> {
                Log.d("EditProfile", "State tidak dikenali")
            }
        }
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomNav) {
                BottomNavigationBar(
                    navController,
                    selectedItem = remember { mutableStateOf("Akun") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background))
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            // Header Section
            HeaderSection(navController)

            // Profile Picture Section
            ProfilePictureSection(
                showPopup = showPopup,
                onPopupChange = { value ->
                    showPopup = value
                    showBottomNav = !value
                }
            )

            // Input Fields Section
            InputFieldsSection(
                name = name,
                onNameChange = { name = it },
                email = email,
                onEmailChange = { email = it },
                phoneNumber = phoneNumber,
                onPhoneChange = { phoneNumber = it }
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Save Changes Button
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Simpan Perubahan",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Display Success or Error Message
            successMessage?.let {
                Text(
                    text = it,
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color(0xFFDCF9D6), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                )
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color(0xFFFFD5D5), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                )
            }
        }

        // Confirmation Dialog
        if (showDialog) {
            ConfirmationDialog(
                message = "Apakah Kamu Yakin Ingin Mengubahnya?",
                onDismissRequest = { showDialog = false },
                onConfirm = {
                    showDialog = false

                    penggunaViewModel.editPengguna(
                        Pengguna(
                            nama_user = name.ifBlank { null },
                            email_user = email.ifBlank { null },
                            telpon_user = phoneNumber.ifBlank { null },
                            foto_profile = null // Foto profil tetap kosong jika tidak ada perubahan
                        )
                    )
                },
                onCancel = { showDialog = false }
            )
        }

        // Popup for Editing Photo
        if (showPopup) {
            PhotoEditPopup(
                onClose = {
                    showPopup = false
                    showBottomNav = true
                },
                onOptionSelected = { option ->
                    // Implement logic for camera/gallery/delete
                    showPopup = false
                    showBottomNav = true
                }
            )
        }
    }
}


// Header Section
@Composable
fun HeaderSection(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                color = colorResource(id = R.color.green),
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )
    ) {
        TopBar(
            title = "Edit Profile",
            navController = navController,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
        )
    }
}

// Profile Picture Section
@Composable
fun ProfilePictureSection(showPopup: Boolean, onPopupChange: (Boolean) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-50).dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(Color.White, CircleShape)
                    .padding(2.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fotoprofil),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            // Camera Icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .offset(x = 6.dp, y = 6.dp)
                    .background(
                        color = colorResource(id = R.color.green_logo),
                        shape = CircleShape
                    )
                    .clickable { onPopupChange(true) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Edit Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = 4.dp, y = 4.dp)
                )
            }
        }
    }
}

// Input Fields Section
@Composable
fun InputFieldsSection(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        InputField(label = "Nama", value = name, onValueChange = onNameChange)
        Spacer(modifier = Modifier.height(16.dp))
        InputField(label = "Email", value = email, onValueChange = onEmailChange)
        Spacer(modifier = Modifier.height(16.dp))
        InputField(label = "Nomor Telepon", value = phoneNumber, onValueChange = onPhoneChange)
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Text(
        text = label,
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    BasicTextField(
        value = value, // Nilai yang ditampilkan di input
        onValueChange = onValueChange, // Fungsi untuk mengubah nilai
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
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
                if (value.isEmpty()) {
                    Text(
                        text = "Masukan $label Baru ",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                innerTextField()
            }
        }
    )
}

// Popup for Editing Photo
@Composable
fun PhotoEditPopup(onClose: () -> Unit, onOptionSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.2f),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            // Photo Edit Popup Implementation
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun HalamanEditProfilePreview() {
//    EdufarmTheme {
//        HalamanEditProfile(
//            navController = rememberNavController()
//        )
//    }
//}