package com.jcorp.risingtest.src.main.login

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentLoginStartBinding
import com.jcorp.risingtest.src.main.login.adapter.LoginPagerAdapter
import java.util.*

class LoginStartFragment : BaseFragment<FragmentLoginStartBinding>(FragmentLoginStartBinding::bind, R.layout.fragment_login_start),
    View.OnClickListener {

    private var pagerTimer : Timer? = Timer()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPager()

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

    override fun onResume() {
        super.onResume()

        val handler = Handler()
        val update = Runnable {
            run() {
                binding.loginStartPager.currentItem = binding.loginStartPager.currentItem + 1
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
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.login_start_btn_kakao -> {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.login_start_layout, LoginUserFragment()).commit()
            }

            R.id.login_start_btn_other -> {
                /*val loginDialog = LoginOtherWayDialog()
                loginDialog.show(requireActivity().supportFragmentManager, "LOGINBOTTOMDIALOG")*/
                val loginDialog = BottomSheetDialog(requireActivity())
                loginDialog.setContentView(R.layout.dialog_login_start_other_way)
                loginDialog.show()
                // 둥글게 만들 방법 찾기

            }
        }
    }

}