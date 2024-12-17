package com.example.edufarm.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.edufarm.R
import com.example.edufarm.ui.theme.poppinsFontFamily

@Composable
fun ConfirmationDialog(
    message: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
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
                    painter = painterResource(id = R.drawable.ic_konfirmasi),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(165.dp)
                        .padding(bottom = 13.dp)
                        .aspectRatio(1f)
                )

                Text(
                    text = message,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp,
                    letterSpacing = (-0.24).sp,
                    color = colorResource(R.color.black),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                            text = "Tidak",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily,
                            lineHeight = 20.sp,
                            letterSpacing = (-0.24).sp,
                            color = colorResource(R.color.black)
                        )
                    }

                    Button(
                        onClick = onConfirm,
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





