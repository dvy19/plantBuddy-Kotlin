package com.example.plantbuddy.NGO

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun createImagePart(
    context: Context,
    imageUri: Uri
): MultipartBody.Part {

    val inputStream = context.contentResolver.openInputStream(imageUri)

    val file = File.createTempFile(
        "ngo_logo",
        ".jpg",
        context.cacheDir
    )

    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    val requestBody = file.asRequestBody("image/*".toMediaType())

    return MultipartBody.Part.createFormData(
        "logo",
        file.name,
        requestBody
    )
}