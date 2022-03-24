package com.jcorp.risingtest.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.jcorp.risingtest.util.LoadingDialog

abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding : B? = null

    protected val binding get() = _binding!!
    lateinit var mLoadingDialog : LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        mLoadingDialog = LoadingDialog.newInstance()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showLoadingDialog() {
            mLoadingDialog.show(requireActivity().supportFragmentManager, "loading")
    }
    fun dismissLoadingDialog() {
        //if(mLoadingDialog.isAdded) {
            mLoadingDialog.dismiss()

    }
}