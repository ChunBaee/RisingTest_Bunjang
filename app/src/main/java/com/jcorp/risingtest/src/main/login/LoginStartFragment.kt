package com.jcorp.risingtest.src.main.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentLoginStartBinding
import com.jcorp.risingtest.src.main.login.adapter.LoginPagerAdapter
import java.util.*

class LoginStartFragment : BaseFragment<FragmentLoginStartBinding>(FragmentLoginStartBinding::bind, R.layout.fragment_login_start),
    View.OnClickListener {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth : FirebaseAuth
    private var pagerTimer : Timer? = Timer()
    lateinit var update : Runnable
    var isFinish = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPager()

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("345942039389-je0lkre25blpih25ah5gpu8ckuve486f.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.loginStartBtnKakao.setOnClickListener(this)
        binding.loginStartBtnOther.setOnClickListener(this)

    }

    private fun setPager() {
        binding.loginStartPager.adapter = LoginPagerAdapter(setPagerList(), requireActivity())
        binding.loginStartPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.loginStartPager.setCurrentItem((Int.MAX_VALUE / 2 + 1), false)

        //인디케이터 추가할 것
    }

    private fun setPagerList() : MutableList<Int> {
        return mutableListOf(R.drawable.img_login_pager_1,R.drawable.img_login_pager_2,R.drawable.img_login_pager_3,R.drawable.img_login_pager_4)
    }

    private fun loginGoogle(idToken : String) {
        auth = Firebase.auth
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val user = auth.currentUser
                    if(user != null) {
                        changeFrag()
                        Log.d("0000", "loginGoogle: ${user.displayName}")
                    }
                }
            }
    }

    private fun changeFrag() {
        requireActivity().supportFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.login_start_layout, LoginUserFragment()).commit()
    }

    override fun onResume() {
        super.onResume()

        val handler = Handler()
            update = Runnable {
                run {
                    while(isFinish) {
                        binding.loginStartPager.currentItem =
                            binding.loginStartPager.currentItem + 1
                    }
                }
            }
        pagerTimer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }

        }, 5000, 5000)
    }

    override fun onPause() {
        super.onPause()
        if(pagerTimer != null) {
            pagerTimer = null

            Log.d("----", "onPause: WHY")
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.login_start_btn_kakao -> {
                changeFrag()
            }

            R.id.login_start_btn_other -> {
                val loginSheetView = layoutInflater.inflate(R.layout.dialog_login_start_other_way, null)
                val loginDialog = BottomSheetDialog(requireActivity())
                loginDialog.setContentView(loginSheetView)
                loginDialog.show()
                loginSheetView.findViewById<LinearLayout>(R.id.dialog_btn_login_phone).setOnClickListener {
                    loginDialog.dismiss()
                    changeFrag()
                }

                loginDialog.findViewById<LinearLayout>(R.id.dialog_btn_login_google)?.setOnClickListener {
                    loginDialog.dismiss()
                    val googleSigninIntent = googleSignInClient.signInIntent
                    startActivityForResult(googleSigninIntent, 1000)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000) {
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                loginGoogle(account.idToken!!)
            } catch (e : ApiException){
            }
        }
    }

}