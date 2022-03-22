package com.jcorp.risingtest.src.main.main

import android.os.Bundle
import android.view.View
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentMainRvRecommendBinding
import com.jcorp.risingtest.src.main.main.adapter.MainRecommendAdapter

class MainRecommendFragment : BaseFragment<FragmentMainRvRecommendBinding>(FragmentMainRvRecommendBinding::bind, R.layout.fragment_main_rv_recommend) {
    private lateinit var adapter : MainRecommendAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRv()
    }

    private fun setRv() {
        binding.itemMainRvRecommend.hasFixedSize()
        adapter = MainRecommendAdapter(requireActivity())
        binding.itemMainRvRecommend.adapter = adapter
        //adapter 체결
    }
}