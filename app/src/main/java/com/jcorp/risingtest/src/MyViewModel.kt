package com.jcorp.risingtest.src

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    //HIDE_BOTTOMVIEW
    var hideBottomView = MutableLiveData<Boolean>(false)

    //Upload_GALLERY
    var mutableUploadUriList = mutableListOf<Uri>()
    private val _upLoadUriList = MutableLiveData<MutableList<Uri>>()
    val upLoadUriList : LiveData<MutableList<Uri>> = _upLoadUriList
    //Upload_Options
    var uploadProductCount = MutableLiveData<Int>(1)
    var uploadProductIsNew = MutableLiveData<Boolean>(false)
    var uploadProductCanExchange = MutableLiveData<Boolean>(false)
    //UploadSafePay
    var uploadIsSafePay = MutableLiveData<Boolean>(true)

    fun setUploadUriList() {
        _upLoadUriList.value = mutableUploadUriList
    }
    fun safePayClicked() {
        uploadIsSafePay.value = !uploadIsSafePay.value!!
    }
}