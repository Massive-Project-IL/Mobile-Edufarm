package com.example.edufarm.akun

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.edufarm.R
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun DukunganScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf("Akun") }
    val messageText = remember { mutableStateOf("") }
    val chatMessages = remember { mutableStateListOf("Halo, selamat datang! Senang bisa mengenal kamu :) yuk beri dukungan dan saran melalui live-chat langsung disini!") }

    Scaffold(
        modifier = Modifier,
        bottomBar = { BottomNavigationBar(navController, selectedItem) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(R.color.background))
                .padding(start = 16.dp, end = 16.dp, top = 5.dp)
        ) {
            TopBar(
                navController = navController,
                title = "Dukungan"
            )


            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState(), reverseScrolling = true),
                verticalArrangement = Arrangement.Bottom
            ) {
                for (message in chatMessages.reversed()) {
                    ChatBubble(
                        text = message,
                        isUser = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText.value,
                    onValueChange = { messageText.value = it },
                    placeholder = { Text("Tuliskan dukungan dan saran disini") },
                    modifier = Modifier
                        .background(colorResource(R.color.white))
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.green),
                        unfocusedBorderColor = colorResource(id = R.color.green),
                        cursorColor = colorResource(id = R.color.green)
                    )
                )
                IconButton(
                    onClick = {
                        if (messageText.value.isNotBlank()) {
                            chatMessages.add(messageText.value)
                            messageText.value = ""
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "Kirim",
                        tint = colorResource(id = R.color.green),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isUser: Boolean) {
    val bubbleColor = if (isUser) colorResource(id = R.color.green) else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isUser) {

            Icon(
                painter = painterResource(id = R.drawable.akun),
                contentDescription = "Icon Profil",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .background(Color.Transparent)
        ) {
            Canvas(
                modifier = Modifier
                    .size(16.dp)
                    .align(if (isUser) Alignment.TopEnd else Alignment.TopStart)
            ) {
                val path = Path().apply {
                    moveTo(0f, size.height / 2)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                    close()
                }
                drawPath(
                    path = path,
                    color = bubbleColor
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = bubbleColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = text,
                    color = if (isUser) Color.White else Color.Black,
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewDukunganScreen() {
    EdufarmTheme {
        DukunganScreen(navController = rememberNavController())
    }
}