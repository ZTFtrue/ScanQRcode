package com.ztftrue.scancode.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.common.Barcode

class MainViewModel : ViewModel() {
    private val barcodesMutable = MutableLiveData<List<Barcode>>()
    fun setBarcodes(barcodes:List<Barcode> ){
        barcodesMutable.postValue(barcodes)
    }
    fun getBarcodes(): MutableLiveData<List<Barcode>> {
        return barcodesMutable
    }
}