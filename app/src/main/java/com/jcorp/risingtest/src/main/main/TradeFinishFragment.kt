package com.jcorp.risingtest.src.main.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentFinishTradeBinding
import com.jcorp.risingtest.src.MyViewModel

class TradeFinishFragment : BaseFragment<FragmentFinishTradeBinding> (FragmentFinishTradeBinding::bind, R.layout.fragment_finish_trade) {
    private val viewModel by activityViewModels<MyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.curLiveChoosePayment.observe(requireActivity(), Observer {
            when(it) {
                0 -> binding.finishTxtChangeable.text = "신용/체크카드로"
                1 -> binding.finishTxtChangeable.text = "카카오페이로"
                2 -> binding.finishTxtChangeable.text = "토스로"
                3 -> binding.finishTxtChangeable.text = "차이로"
                4 -> binding.finishTxtChangeable.text = "간편계좌결제로"
                5 -> binding.finishTxtChangeable.text = "휴대폰결제로"
                6 -> binding.finishTxtChangeable.text = "편의점결제로"
                else -> binding.finishTxtChangeable.text = "번개포인트로"
            }
        })

        binding.finishBtnFinish.setOnClickListener {
            viewModel.productPrice.value = 0
            requireActivity().supportFragmentManager.beginTransaction().remove(this@TradeFinishFragment).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}