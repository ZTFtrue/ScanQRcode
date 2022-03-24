package com.ztftrue.scancode.fragments

import android.annotation.SuppressLint
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.ztftrue.scancode.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val barcodesMutable = MutableLiveData<List<Barcode>>()
    private val scanner = BarcodeScanning.getClient(/*options*/)

    private fun setBarcodes(barcodes: List<Barcode>) {
        barcodesMutable.postValue(barcodes)
    }

    fun getBarcodes(): MutableLiveData<List<Barcode>> {
        return barcodesMutable
    }

    fun scanBarcode(uri: Uri, activity: FragmentActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            scanner.process(InputImage.fromFilePath(activity, uri))
                .addOnSuccessListener { barcodes ->
                    if (barcodes != null && barcodes.isNotEmpty()) {
                        activity.runOnUiThread {
                            setBarcodes(barcodes)
                            Navigation.findNavController(
                                activity, R.id.fragment_container
                            ).navigate(
                                CameraFragmentDirections.actionCameraToGallery("")
                            )
                        }
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun scanBarcode(imageProxy: ImageProxy, activity: FragmentActivity) {
        viewModelScope.launch {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image =
                    InputImage.fromMediaImage(
                        mediaImage,
                        imageProxy.imageInfo.rotationDegrees
                    )
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes != null && barcodes.isNotEmpty()) {
                            activity.runOnUiThread {
                                setBarcodes(barcodes)
                                Navigation.findNavController(
                                    activity, R.id.fragment_container
                                ).navigate(
                                    CameraFragmentDirections.actionCameraToGallery("")
                                )
                            }
                        }
                        imageProxy.close()
                    }
                    .addOnFailureListener {
                        imageProxy.close()
//                                it.printStackTrace()
                    }
            }
        }
    }
}