package com.example.edufarm.akun

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.edufarm.R
import com.example.edufarm.data.model.Pengguna
import com.example.edufarm.data.utils.copyUriToFile
import com.example.edufarm.data.utils.saveBitmapToFile
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
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Periksa izin kamera
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher untuk meminta izin kamera
    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
        if (!isGranted) {
            Toast.makeText(context, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher untuk kamera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            val savedUri = saveBitmapToFile(context, bitmap)
            if (savedUri != null) {
                imageUri = savedUri
                Log.d("EditProfile", "Bitmap saved to: $savedUri")
            } else {
                Toast.makeText(context, "Gagal menyimpan gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Launcher untuk galeri
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val savedUri = copyUriToFile(context, uri)
            if (savedUri != null) {
                imageUri = savedUri
                Log.d("EditProfile", "File disalin ke: $savedUri")
            } else {
                Toast.makeText(context, "Gagal menyimpan gambar dari galeri", Toast.LENGTH_SHORT).show()
            }
        }
    }


    var pengguna by remember { mutableStateOf<Pengguna?>(null) }
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
        penggunaViewModel.getPengguna()
    }

    LaunchedEffect(penggunaState) {
        if (penggunaState is ProfileState.Success) {
            pengguna = (penggunaState as ProfileState.Success).pengguna
            name = pengguna?.nama_user ?: ""
            email = pengguna?.email_user ?: ""
            phoneNumber = pengguna?.telpon_user ?: ""
            imageUri = null

            Log.d("EditProfile", "Foto profile URL: ${pengguna?.foto_profile}")
        }
    }

    LaunchedEffect(editState) {
        when (editState) {
            is EditProfileState.Success -> {
                val updatedPengguna = (editState as EditProfileState.Success).pengguna
                if (updatedPengguna != null) {
                    successMessage = "Profil berhasil diperbarui!"
                    errorMessage = null
                    navController.navigate(Routes.NOTIFIKASI_PROFILE) {
                        popUpTo(Routes.HALAMAN_EDIT_PROFILE) { inclusive = true }
                    }
                } else {
                    errorMessage =
                        "Profil berhasil diperbarui, tetapi data pengguna tidak diperbarui dari server."
                    successMessage = null
                }
            }

            is EditProfileState.SuccessMessage -> {
                successMessage = (editState as EditProfileState.SuccessMessage).message
                errorMessage = null
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
            HeaderSection(navController)

            ProfilePictureSection(
                imageUri = imageUri,
                serverImageUrl = pengguna?.foto_profile,
                onPopupChange = { value ->
                    showPopup = value
                    showBottomNav = !value
                }
            )
            InputFieldsSection(
                name = name,
                onNameChange = { name = it },
//                email = email,
//                onEmailChange = { email = it },
                phoneNumber = phoneNumber,
                onPhoneChange = { phoneNumber = it }
            )

            Spacer(modifier = Modifier.height(60.dp))
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
        }

        if (showDialog) {
            ConfirmationDialog(
                message = "Apakah Kamu Yakin Ingin Mengubahnya?",
                onDismissRequest = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    penggunaViewModel.editPengguna(
                        namaUser = name.ifBlank { null } ?: "",
                        emailUser = email.ifBlank { null } ?: "",
                        telponUser = phoneNumber.ifBlank { null } ?: "",
                        fotoUri = imageUri,
                        context = context
                    )
                },
                onCancel = { showDialog = false }
            )
        }

        if (showPopup) {
            PhotoEditPopup(
                onClose = {
                    showPopup = false
                    showBottomNav = true
                },
                onOptionSelected = { option ->
                    showPopup = false
                    showBottomNav = true
                    when (option) {
                        "Camera" -> {
                            if (hasCameraPermission) {
                                cameraLauncher.launch()
                            } else {
                                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }

                        "Gallery" -> galleryLauncher.launch("image/*")
                    }
                }
            )
        }
    }
}

@Composable
fun ProfilePictureSection(
    imageUri: Uri?,  // Untuk gambar lokal yang diambil dari kamera/galeri
    serverImageUrl: String?,  // URL gambar dari server
    onPopupChange: (Boolean) -> Unit
) {
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
                when {
                    imageUri != null -> {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUri),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    !serverImageUrl.isNullOrEmpty() -> {
                        Image(
                            painter = rememberAsyncImagePainter(model = serverImageUrl),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    else -> {
                        Image(
                            painter = painterResource(id = R.drawable.default_image),
                            contentDescription = "Default Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color(0xFF92D5B6), shape = CircleShape)
                        )
                    }
                }
            }

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


@Composable
fun PhotoEditPopup(
    onClose: () -> Unit,
    onOptionSelected: (String) -> Unit
) {
    Dialog(onDismissRequest = { onClose() }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(24.dp))
                    Text(
                        text = "Foto Profil",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onClose() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOptionSelected("Camera")
                            onClose()
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.camera_2),
                        contentDescription = "Kamera",
                        tint = colorResource(R.color.green_jadwal),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Kamera",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOptionSelected("Gallery")
                            onClose()
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.photograph),
                        contentDescription = "Galeri",
                        tint = colorResource(R.color.green_jadwal),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Galeri",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

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

@Composable
fun InputFieldsSection(
    name: String,
    onNameChange: (String) -> Unit,
//    email: String,
//    onEmailChange: (String) -> Unit,
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
//        InputField(label = "Email", value = email, onValueChange = onEmailChange)
//        Spacer(modifier = Modifier.height(16.dp))
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
        value = value,
        onValueChange = onValueChange,
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



