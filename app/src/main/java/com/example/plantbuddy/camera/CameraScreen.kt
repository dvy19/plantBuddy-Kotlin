package com.example.plantbuddy.camera



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun CameraScreen(
    mainNavController: NavController
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {
        PreviewView(context)
    }

    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    var lensFacing by remember {
        mutableStateOf(
            CameraSelector.LENS_FACING_BACK
        )
    }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { }

    LaunchedEffect(lensFacing) {

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            cameraPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    LaunchedEffect(Unit) {

        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({

            val cameraProvider =
                cameraProviderFuture.get()

            /*
            val preview =
                Preview.Builder().build()

             */

            val preview =
                Preview.Builder()
                    .setTargetAspectRatio(
                        AspectRatio.RATIO_4_3
                    )
                    .build()


            preview.setSurfaceProvider(
                previewView.surfaceProvider
            )



            try {

                cameraProvider.unbindAll()

                val cameraSelector =
                    CameraSelector.Builder()
                        .requireLensFacing(
                            lensFacing
                        )
                        .build()

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (e: Exception) {

                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(context))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        AndroidView(
            factory = {
                previewView.apply {

                    scaleType =
                        PreviewView.ScaleType.FIT_CENTER
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                )
                .align(Alignment.Center)

        )

        Button(
            onClick = {

                val file =
                    createImageFile(context)

                val outputOptions =
                    ImageCapture.OutputFileOptions
                        .Builder(file)
                        .build()

                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),

                    object : ImageCapture.OnImageSavedCallback {

                        override fun onImageSaved(
                            outputFileResults:
                            ImageCapture.OutputFileResults
                        ) {

                            val encodedPath =
                                Uri.encode(file.absolutePath)

                            mainNavController.navigate(
                                "add_note/$encodedPath"
                            )
                        }

                        override fun onError(
                            exception: ImageCaptureException
                        ) {

                            exception.printStackTrace()
                        }
                    }
                )
            },

            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {

            Text("Capture")
        }

        IconButton(
            onClick = {

                lensFacing =
                    if (lensFacing ==
                        CameraSelector.LENS_FACING_BACK
                    ) {
                        CameraSelector.LENS_FACING_FRONT
                    } else {
                        CameraSelector.LENS_FACING_BACK
                    }




            }
        ) {
            Icon(
                imageVector = Icons.Default.Cameraswitch,
                contentDescription = "Switch Camera"
            )
        }
    }
}