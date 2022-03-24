package com.jcorp.risingtest.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.util.LoadingDialog

abstract class BaseActivity<B : ViewBinding>(private val inflate : (LayoutInflater) -> B) : AppCompatActivity() {
    protected lateinit var binding : B
        private set
    //lateinit var mLoadingDialog : LoadingDialog

    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    /*fun showLoadingDialog(context : Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show(supportFragmentManager, "loading")
    }
    fun dismissLoadingDialog() {
        if(mLoadingDialog.isAdded) {
            mLoadingDialog.dismiss()
        }
    }*/


}