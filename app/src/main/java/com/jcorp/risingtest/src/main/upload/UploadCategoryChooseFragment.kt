package com.jcorp.risingtest.src.main.upload

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentUploadChooseCategoryBinding
import com.jcorp.risingtest.src.MyViewModel

class UploadCategoryChooseFragment : BaseFragment<FragmentUploadChooseCategoryBinding>(FragmentUploadChooseCategoryBinding::bind, R.layout.fragment_upload_choose_category){
    private val viewModel by activityViewModels<MyViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.upload_choose_category_container, UploadCategoryFormFragment(0)).commit()

    }
}