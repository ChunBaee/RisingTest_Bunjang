package com.jcorp.risingtest.src.main.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationBarView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseActivity
import com.jcorp.risingtest.databinding.ActivityMainBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.main.model.*
import com.jcorp.risingtest.src.main.main.util.MainActivityView
import com.jcorp.risingtest.src.main.main.util.MainService
import com.jcorp.risingtest.src.main.upload.UploadFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), MainActivityView {

    private val viewModel by viewModels<MyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setView()
        getCurUserData()
        setBaseFragment()
        observe()
    }

    private fun setBaseFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, MainHomeFragment()).commit()
    }

    private fun setView() {
        binding.mainBottomNavigation.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId) {
                    R.id.bottom_main_home -> {
                        supportFragmentManager.beginTransaction().replace(R.id.main_container, MainHomeFragment()).commit()
                    }

                    R.id.bottom_main_search -> {}

                    R.id.bottom_main_upload -> {
                        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.main_container, UploadFragment()).commit()
                    }

                    R.id.bottom_main_chat -> {}

                    R.id.bottom_main_profile -> {}
                }
                return true
            }

        })
    }

    private fun observe() {
        viewModel.hideBottomView.observe(this, Observer {
            when(it) {
                true -> binding.mainBottomNavigation.visibility = View.GONE
                false -> binding.mainBottomNavigation.visibility = View.VISIBLE
            }
        })
    }

    private fun getCurUserData() {
        MainService(this).getCurUserData()
    }

    override fun onGetDataSuccess(response: CurUserData) {
        viewModel.setCurUserData(response.result)
        Log.d("0000", "onGetDataSuccess: ${response.result.userName}")
    }

    override fun onHomeBannerDataSuccess(response: MainBannerData) {
    }

    override fun onHomeCategoryDataSuccess(response: HomeCategoryData) {
    }

    override fun onRecommendDataSuccess(response: MainRecommendRvItem) {
    }

    override fun onProductDetailDataSuccess(response: ProductDetailData) {
    }
}