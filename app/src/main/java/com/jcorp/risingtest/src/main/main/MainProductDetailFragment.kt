package com.jcorp.risingtest.src.main.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentMainItemDetailBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.main.model.CurUserData
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem
import com.jcorp.risingtest.src.main.main.util.MainActivityView

class MainProductDetailFragment : BaseFragment<FragmentMainItemDetailBinding>(FragmentMainItemDetailBinding::bind, R.layout.fragment_main_item_detail), MainActivityView {
    private val viewModel by activityViewModels<MyViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("---", "onViewCreated: HI")
        viewModel.hideBottomView.value = true
    }


    override fun onGetDataSuccess(response: CurUserData) {
    }

    override fun onRecommendDataSuccess(response: MainRecommendRvItem) {
    }

}