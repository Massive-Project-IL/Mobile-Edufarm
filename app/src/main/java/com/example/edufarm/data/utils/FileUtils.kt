package com.example.edufarm.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream

fun copyUriToFile(context: Context, uri: Uri): Uri? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "profile_picture_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        Uri.fromFile(file)
    } catch (e: Exception) {
        Log.e("EditProfile", "Error copying URI to file: ${e.message}")
        null
    }
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val file = File(context.cacheDir, "profile_picture_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        Uri.fromFile(file)
    } catch (e: Exception) {
        Log.e("EditProfile", "Error saving bitmap to file: ${e.message}")
        null
    }
}
