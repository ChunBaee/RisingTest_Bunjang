package com.jcorp.risingtest.src.main.login

import android.os.Bundle
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseActivity
import com.jcorp.risingtest.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.login_container, LoginStartFragment()).commit()
    }
}