package com.jcorp.risingtest.src.main.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.android.material.navigation.NavigationBarView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseActivity
import com.jcorp.risingtest.databinding.ActivityMainBinding
import com.jcorp.risingtest.src.main.upload.UploadFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setView()
        setBaseFragment()
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
}