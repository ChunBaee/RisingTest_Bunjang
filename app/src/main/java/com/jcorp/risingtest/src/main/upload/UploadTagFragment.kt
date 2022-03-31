package com.jcorp.risingtest.src.main.upload

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentUploadTagBinding
import com.jcorp.risingtest.src.MyViewModel

class UploadTagFragment : BaseFragment<FragmentUploadTagBinding>(
    FragmentUploadTagBinding::bind,
    R.layout.fragment_upload_tag
),
    View.OnClickListener {

    private val viewModel by activityViewModels<MyViewModel>()
    private var tagList: String = ""
    private var tagCount = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadTagBack.setOnClickListener(this)
        binding.uploadTagSave.setOnClickListener(this)

        binding.uploadTagEdtTags.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tagCount = 0
                tagList = binding.uploadTagEdtTags.text.toString()
                for(i in 0 until tagList.length) {
                    if(tagList[i].toString() == "#") {
                        tagCount++
                    }
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                /*if(binding.uploadTagEdtTags.text.toString().endsWith(" ")) {
                    binding.uploadTagEdtTags.setText("$tagList #")
                    binding.uploadTagEdtTags.setSelection(binding.uploadTagEdtTags.length())
                    tagCount++
                }*/
            }

        })

        binding.uploadTagEdtTags.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == KeyEvent.KEYCODE_SPACE && p2?.action == KeyEvent.ACTION_UP && tagCount < 4) {
                    binding.uploadTagEdtTags.setText("$tagList #")
                    binding.uploadTagEdtTags.setSelection(binding.uploadTagEdtTags.length())
                } else if (p1 == KeyEvent.KEYCODE_SPACE && tagCount >= 4) {
                    return true
                }
                return false
            }
        })

    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.upload_tag_back-> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@UploadTagFragment)
                    .commit()
                requireActivity().supportFragmentManager.popBackStack()
            }
            R.id.upload_tag_save -> {
                tagList = if(binding.uploadTagEdtTags.text.isEmpty()) {
                    ""
                } else {
                    "#" + binding.uploadTagEdtTags.text.toString()
                }
                viewModel.uploadTagList = tagList.split(" ").toMutableList()

                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@UploadTagFragment)
                    .commit()
                requireActivity().supportFragmentManager.popBackStack()
            }

        }
    }
}