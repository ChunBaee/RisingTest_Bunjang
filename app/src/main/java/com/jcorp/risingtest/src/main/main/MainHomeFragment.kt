package com.jcorp.risingtest.src.main.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.alpha
import androidx.fragment.app.Fragment
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentMainHomeBinding

class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(FragmentMainHomeBinding::bind, R.layout.fragment_main_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()

        //툴바 투명도 조절
        val outLocation = IntArray(2)
        binding.mainHomeToolbar.getLocationOnScreen(outLocation)
        binding.mainHomeScrollview.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                binding.mainHomeToolbar.background.alpha = ((p2 * 0.425).toInt())
                Log.d("----", "onScrollChange: ${binding.mainHomeBtnMenu.background.alpha} / $p2")
                if(p2 > 300) {
                    binding.mainHomeBtnMenu.background.alpha = ((p2 - 300 )* 0.85).toInt()
                    binding.mainHomeBtnAlarm.background.alpha = ((p2 - 300 )* 0.85).toInt()
                    binding.mainHomeBtnSearch.background.alpha = ((p2 - 300 )* 0.85).toInt()
                    binding.mainHomeBtnMenu.background.setTint(requireActivity().resources.getColor(R.color.black))
                    binding.mainHomeBtnAlarm.background.setTint(requireActivity().resources.getColor(R.color.black))
                    binding.mainHomeBtnSearch.background.setTint(requireActivity().resources.getColor(R.color.black))
                } else if(p2 < 300) {
                    binding.mainHomeBtnMenu.background.alpha = (255 - (p2 * 0.85).toInt())
                    binding.mainHomeBtnAlarm.background.alpha = (255 - (p2 * 0.5).toInt())
                    binding.mainHomeBtnSearch.background.alpha = (255 - (p2 * 0.5).toInt())
                    binding.mainHomeBtnMenu.background.setTint(requireActivity().resources.getColor(R.color.white))
                    binding.mainHomeBtnAlarm.background.setTint(requireActivity().resources.getColor(R.color.white))
                    binding.mainHomeBtnSearch.background.setTint(requireActivity().resources.getColor(R.color.white))
                }
            }

        })
    }

    private fun setToolbar() {
        activity?.window?.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        binding.mainHomeToolbar.bringToFront()
        binding.mainHomeToolbar.background.alpha = 0


    }
}