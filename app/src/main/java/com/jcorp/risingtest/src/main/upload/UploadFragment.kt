package com.jcorp.risingtest.src.main.upload

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentUploadHomeBinding
import com.jcorp.risingtest.src.main.upload.adapter.UploadRvAdapter
import java.text.DecimalFormat

class UploadFragment : BaseFragment<FragmentUploadHomeBinding>(FragmentUploadHomeBinding::bind, R.layout.fragment_upload_home),
    View.OnClickListener {
    private val GALLERY_REQUEST_CODE = 1000
    private lateinit var adapter : UploadRvAdapter
    private var photoList = mutableListOf<Uri>()

    private var isFeeIncluded = false
    private var lastWonTxt = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadBtnGallery.setOnClickListener(this)
        binding.uploadBtnIncludeFee.setOnClickListener(this)

        setRv()
        setView()
    }

    private fun setRv() {
        binding.uploadRvGallery.hasFixedSize()
        adapter = UploadRvAdapter(requireActivity())
        binding.uploadRvGallery.adapter = adapter
        setNotify()

        adapter.deleteClickListener(object : UploadRvAdapter.DeleteClickListener {
            override fun onDeleteClick(view: View, position: Int) {
                photoList.removeAt(position)
                setNotify()
            }

        })
    }

    private fun setView() {
        binding.uploadToolbar.bringToFront()

        binding.uploadEdtProductPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.isNotEmpty() == true && p0.toString() != lastWonTxt) {
                    val myFormatter = DecimalFormat("###,###")
                    lastWonTxt = myFormatter.format((p0.toString().replace(",","")).toDouble())
                    binding.uploadEdtProductPrice.setText(lastWonTxt)
                    binding.uploadEdtProductPrice.setSelection(lastWonTxt.length)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun setNotify() {
        adapter.setList(photoList)
        binding.uploadTxtGallery.text = "${photoList.size}/12"
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.upload_btn_gallery -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, GALLERY_REQUEST_CODE)
            }

            R.id.upload_btn_include_fee -> {
                when(isFeeIncluded) {
                    true -> {
                        binding.uploadCheckIncludeFee.setImageResource(R.drawable.icon_upload_include_fee)
                    }
                    false -> {
                        binding.uploadCheckIncludeFee.setImageResource(R.drawable.icon_upload_uninclude_fee)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) {
            return
        }
        when(requestCode) {
            GALLERY_REQUEST_CODE -> {
                val uri = data!!.data as Uri
                photoList.add(uri)
                Log.d("----", "onActivityResult: ${photoList}")
                setNotify()

            }
        }
    }

}