package com.jcorp.risingtest.src.main.upload

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentUploadCategoryFormBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.upload.adapter.CategoryRvAdapter
import com.jcorp.risingtest.src.main.upload.model.UploadCategoryData
import com.jcorp.risingtest.src.main.upload.model.UploadLocationData
import com.jcorp.risingtest.src.main.upload.util.UploadCategoryView
import com.jcorp.risingtest.src.main.upload.util.UploadService

class UploadCategoryFormFragment(depth: Int) : BaseFragment<FragmentUploadCategoryFormBinding>(FragmentUploadCategoryFormBinding::bind, R.layout.fragment_upload_category_form),
    UploadCategoryView {
    private lateinit var categoryAdapter : CategoryRvAdapter
    private val viewModel by activityViewModels<MyViewModel>()
    private var mDepth = depth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(mDepth) {
            0 -> {
                binding.categoryMainTitleImgNext.visibility = View.GONE
                binding.categorySubTitle.visibility = View.GONE
                UploadService(this).getLargeCategory()
            }
            1 -> {
                binding.categoryMainTitle.text = viewModel.uploadLargeCategoryName.value.toString()
                binding.categoryMainTitleImgNext.visibility = View.GONE
                binding.categorySubTitle.visibility = View.GONE
                UploadService(this).getMiddleCategory(viewModel.uploadLargeCategoryIdx.value!!)

            }
            2 -> {
                binding.categoryMainTitle.text = viewModel.uploadLargeCategoryName.value.toString()
                binding.categoryMainTitle.setTypeface(null, Typeface.NORMAL)
                binding.categoryMainTitleImgNext.visibility = View.VISIBLE
                binding.categorySubTitle.text = viewModel.uploadMiddleCategoryName.value.toString()
                binding.categorySubTitle.visibility = View.VISIBLE
                UploadService(this).getSmallCategory(viewModel.uploadMiddleCategoryIdx.value!!)
            }
        }

        setRv()
    }

    private fun setRv() {
        binding.categoryRvCategories.hasFixedSize()
        categoryAdapter = CategoryRvAdapter(requireActivity())
        binding.categoryRvCategories.adapter = categoryAdapter

        categoryAdapter.categoryClickListener(object : CategoryRvAdapter.CategoryClickListener {
            override fun onClick(view: View, position: Int, name: String) {
                when(mDepth) {
                    0 -> {
                        viewModel.uploadLargeCategoryName.value = name
                        viewModel.uploadLargeCategoryIdx.value = position
                        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.upload_choose_category_container, UploadCategoryFormFragment(1)).commit()
                    }

                    1-> {
                        viewModel.uploadMiddleCategoryName.value = name
                        viewModel.uploadMiddleCategoryIdx.value = position
                        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.upload_choose_category_container, UploadCategoryFormFragment(2)).commit()
                    }

                    2-> {
                        viewModel.uploadSmallCategoryName.value = name
                        viewModel.uploadSmallCategoryIdx.value = position

                        viewModel.categorySelected.value = true

                        //???????????? ?????????????????? ??????
                        requireActivity().supportFragmentManager.beginTransaction().remove(this@UploadCategoryFormFragment).commit()
                        requireActivity().supportFragmentManager.popBackStack()
                        requireActivity().supportFragmentManager.beginTransaction().remove(this@UploadCategoryFormFragment).commit()
                        requireActivity().supportFragmentManager.popBackStack()
                        requireActivity().supportFragmentManager.beginTransaction().remove(this@UploadCategoryFormFragment).commit()
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }
        })
    }

    override fun onGetLargeCategorySuccess(response: UploadCategoryData) {
        categoryAdapter.setData(response.result.toMutableList())
    }

    override fun onGetMiddleCategorySuccess(response: UploadCategoryData) {
        categoryAdapter.setData(response.result.toMutableList())
    }

    override fun onGetLocationData(response: UploadLocationData) {
    }

    override fun onGetSmallCategorySuccess(response: UploadCategoryData) {
        categoryAdapter.setData(response.result.toMutableList())
    }

    override fun onUploadUserProductSuccess(response: BaseData) {
    }
}