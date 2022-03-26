package com.jcorp.risingtest.src.main.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentMainItemDetailBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.main.adapter.DetailImageAdapter
import com.jcorp.risingtest.src.main.main.adapter.DetailOtherProductAdapter
import com.jcorp.risingtest.src.main.main.adapter.DetailTagAdapter
import com.jcorp.risingtest.src.main.main.model.CurUserData
import com.jcorp.risingtest.src.main.main.model.HomeCategoryData
import com.jcorp.risingtest.src.main.main.model.MainBannerData
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem
import com.jcorp.risingtest.src.main.main.util.MainActivityView

class MainProductDetailFragment : BaseFragment<FragmentMainItemDetailBinding>(FragmentMainItemDetailBinding::bind, R.layout.fragment_main_item_detail), MainActivityView {
    private val viewModel by activityViewModels<MyViewModel>()

    private lateinit var photoAdapter : DetailImageAdapter
    private lateinit var tagAdapter : DetailTagAdapter
    private lateinit var otherProductAdapter : DetailOtherProductAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hideBottomView.value = true

        setImgPager()
        setTagList()
        setOtherProductList()
        setReviewList()

    }

    private fun setImgPager() {
        photoAdapter = DetailImageAdapter(requireActivity())
        binding.productDetailPager.adapter = photoAdapter
    }

    private fun setTagList() {
        tagAdapter = DetailTagAdapter()
        binding.productDetailTagRv.hasFixedSize()
        binding.productDetailTagRv.adapter = tagAdapter
    }

    private fun setOtherProductList() {
        otherProductAdapter = DetailOtherProductAdapter(requireActivity())
        binding.productDetailOtherProductRv.hasFixedSize()
        binding.productDetailOtherProductRv.adapter = otherProductAdapter
    }

    private fun setReviewList() {

    }

    override fun onPause() {
        viewModel.hideBottomView.value = false
        super.onPause()
    }


    override fun onGetDataSuccess(response: CurUserData) {
    }

    override fun onHomeBannerDataSuccess(response: MainBannerData) {
    }

    override fun onHomeCategoryDataSuccess(response: HomeCategoryData) {
    }

    override fun onRecommendDataSuccess(response: MainRecommendRvItem) {
    }

}