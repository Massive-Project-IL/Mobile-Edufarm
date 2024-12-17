package com.example.edufarm.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavController
import com.example.edufarm.R
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.LoginViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun OrSeparator(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Gray)
        )
        Text(
            text = text,
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
fun SocialMediaLogin(viewModel: LoginViewModel, navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    val secureNonce = UUID.randomUUID().toString()

    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId("28052020880-tl32hke1qhjb9men27jb1m8ph87dgpi1.apps.googleusercontent.com") // Web Client ID
        .setNonce(secureNonce)
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    IconButton(onClick = {
        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                handleGoogleSignInResult(result, viewModel, navController)
            } catch (e: GetCredentialException) {
                Log.e("CredentialManager", "Error: ${e.localizedMessage}")
            }
        }
    }) {
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "Google Login",
            modifier = Modifier.size(32.dp)
        )
    }
}

fun handleGoogleSignInResult(
    result: GetCredentialResponse,
    viewModel: LoginViewModel,
    navController: NavController
) {
    val credential = result.credential

    if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        try {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken

            Log.d("GoogleSignIn", "ID Token: $idToken")
            viewModel.loginWithGoogle(idToken)
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "Error parsing ID token: ${e.localizedMessage}")
        }
    } else {
        Log.e("GoogleSignIn", "Unexpected credential type: ${credential.type}")
    }
}