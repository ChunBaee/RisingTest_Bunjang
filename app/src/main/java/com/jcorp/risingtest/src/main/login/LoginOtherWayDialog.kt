package com.jcorp.risingtest.src.main.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.DialogLoginStartOtherWayBinding

class LoginOtherWayDialog : BottomSheetDialogFragment(){
    private lateinit var binding : DialogLoginStartOtherWayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLoginStartOtherWayBinding.inflate(inflater, container,false)

        setDialog()


        return binding.root
    }

    private fun setDialog() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.drawable.dialog_login_start_other)
    }
}