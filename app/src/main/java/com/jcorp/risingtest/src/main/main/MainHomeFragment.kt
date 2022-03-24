package com.jcorp.risingtest.src.main.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentMainHomeBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.main.adapter.MainCategoryAdapter
import com.jcorp.risingtest.src.main.main.adapter.MainRecommendAdapter
import com.jcorp.risingtest.src.main.main.model.CurUserData
import com.jcorp.risingtest.src.main.main.model.MainHomeCategoryData
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem
import com.jcorp.risingtest.src.main.main.util.MainActivityView
import com.jcorp.risingtest.src.main.main.util.MainService

class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(
    FragmentMainHomeBinding::bind,
    R.layout.fragment_main_home
), MainActivityView {
    private val viewModel by activityViewModels<MyViewModel>()
    private lateinit var categoryAdapter: MainCategoryAdapter
    private lateinit var recommendAdapter : MainRecommendAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hideBottomView.value = false

        setToolbar()
        setTab()
        setCategory()
        setRecommend()

        viewModel.testImgUrl.observe(requireActivity(), Observer {
            Log.d("0000", "onViewCreated: $it")
            //lide.with(requireActivity()).load(it).into(binding.img)
    })

    //툴바 투명도 조절
    val outLocation = IntArray(2)
    val tabLocation = IntArray(2)
    binding.mainHomeToolbar.getLocationOnScreen(outLocation)
    binding.tabs.getLocationOnScreen(tabLocation)
    binding.mainHomeScrollview.setOnScrollChangeListener(
    object : View.OnScrollChangeListener {
        override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {

            if (p2 > 600) {
                requireActivity().window.statusBarColor =
                    Color.argb(255, 255, 255, 255)
            } else {
                requireActivity().window.statusBarColor =
                    Color.argb((p2 * 0.425).toInt(), 255, 255, 255)
            }

            binding.mainHomeToolbar.background.alpha = ((p2 * 0.425).toInt())
            if (p2 > 300) {
                binding.mainHomeBtnMenu.background.alpha = ((p2 - 300) * 0.85).toInt()
                binding.mainHomeBtnAlarm.background.alpha = ((p2 - 300) * 0.85).toInt()
                binding.mainHomeBtnSearch.background.alpha = ((p2 - 300) * 0.85).toInt()
                binding.mainHomeBtnMenu.background.setTint(requireActivity().resources.getColor(R.color.black))
                binding.mainHomeBtnAlarm.background.setTint(requireActivity().resources.getColor(R.color.black))
                binding.mainHomeBtnSearch.background.setTint(requireActivity().resources.getColor(R.color.black))
            } else if (p2 < 300) {
                binding.mainHomeBtnMenu.background.alpha = (255 - (p2 * 0.85).toInt())
                binding.mainHomeBtnAlarm.background.alpha = (255 - (p2 * 0.5).toInt())
                binding.mainHomeBtnSearch.background.alpha = (255 - (p2 * 0.5).toInt())
                binding.mainHomeBtnMenu.background.setTint(requireActivity().resources.getColor(R.color.white))
                binding.mainHomeBtnAlarm.background.setTint(requireActivity().resources.getColor(R.color.white))
                binding.mainHomeBtnSearch.background.setTint(requireActivity().resources.getColor(R.color.white))
            }
            if ((binding.mainHomeToolbar.bottom + p2) >= binding.tabs.top) {
                binding.tabs.translationY =
                    ((p2 + binding.mainHomeToolbar.bottom) - binding.tabs.top).toFloat()
                Log.d("----", "onScrollChange: IT IS")
            } else {
                binding.tabs.y = 1543F
            }
            Log.d(
                "----",
                "Toolbar : ${binding.mainHomeToolbar.bottom} / Tab: ${binding.tabs.top} / $p2"
            )
        }
    })
}

private fun setToolbar() {
    activity?.window?.apply {
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
    binding.mainHomeToolbar.bringToFront()
    binding.tabs.bringToFront()
    binding.mainHomeToolbar.background.alpha = 0
}

private fun setTab() {

}

private fun setCategory() {
    binding.mainHomeRvCategory.hasFixedSize()
    categoryAdapter = MainCategoryAdapter(requireActivity())
    binding.mainHomeRvCategory.adapter = categoryAdapter

    //임시데이터
    val list = mutableListOf<MainHomeCategoryData>()
    for (i in 0 until 14) {
        list.add(MainHomeCategoryData(i, i.toString(), R.drawable.ic_launcher_background))
    }
    categoryAdapter.setList(list)
}

    private fun setRecommend() {
        MainService(this).getRecommendRvData()
        recommendAdapter = MainRecommendAdapter(requireActivity())
        binding.mainHomeRecommendRv.hasFixedSize()
        binding.mainHomeRecommendRv.adapter = recommendAdapter

        recommendAdapter.detailClickListener(object : MainRecommendAdapter.DetailClickListener{
            override fun onClick(view: View, position: Int) {
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.main_container, MainProductDetailFragment()).commit()
            }

        })
    }

    override fun onGetDataSuccess(response: CurUserData) {
    }

    override fun onRecommendDataSuccess(response: MainRecommendRvItem) {
        recommendAdapter.setRecommendList(response.result.toMutableList())
    }
}