package com.jcorp.risingtest.src

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.jcorp.risingtest.src.main.main.model.CurUserData
import com.jcorp.risingtest.src.main.main.model.Result

class MyViewModel : ViewModel() {
    var testImgUrl = MutableLiveData<String>()
    //Current User Data
    var curUserData = MutableLiveData<Result>()

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
    //UploadCategoryChoose
    var uploadLargeCategoryName = MutableLiveData<String>()
    var uploadLargeCategoryIdx = MutableLiveData<Int>(0)

    var uploadMiddleCategoryName = MutableLiveData<String>()
    var uploadMiddleCategoryIdx = MutableLiveData<Int>(0)

    var uploadSmallCategoryName = MutableLiveData<String>()
    var uploadSmallCategoryIdx = MutableLiveData<Int>(0)

    var categorySelected = MutableLiveData<Boolean>(false)
    //UploadTag
    var uploadTagList = MutableLiveData<MutableList<String>>()

    fun setCurUserData(result : Result) {
        curUserData.value = result
    }

    fun setUploadUriList() {
        _upLoadUriList.value = mutableUploadUriList
    }
    fun safePayClicked() {
        uploadIsSafePay.value = !uploadIsSafePay.value!!
    }
}