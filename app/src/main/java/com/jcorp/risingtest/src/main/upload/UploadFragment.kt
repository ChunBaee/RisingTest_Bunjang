package com.jcorp.risingtest.src.main.upload

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentUploadHomeBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.upload.adapter.TagRvAdapter
import com.jcorp.risingtest.src.main.upload.adapter.UploadRvAdapter
import com.jcorp.risingtest.src.main.upload.model.UploadCategoryData
import com.jcorp.risingtest.src.main.upload.model.UploadMyProductData
import com.jcorp.risingtest.src.main.upload.model.productImgUrl
import com.jcorp.risingtest.src.main.upload.model.tagName
import com.jcorp.risingtest.src.main.upload.util.UploadCategoryView
import com.jcorp.risingtest.src.main.upload.util.UploadService
import com.jcorp.risingtest.util.KeyboardVisibilityUtils
import java.text.DecimalFormat

class UploadFragment : BaseFragment<FragmentUploadHomeBinding>(
    FragmentUploadHomeBinding::bind,
    R.layout.fragment_upload_home
),
    View.OnClickListener, UploadCategoryView {
    private val viewModel by activityViewModels<MyViewModel>()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    private val GALLERY_REQUEST_CODE = 1000
    private lateinit var uploadAdapter: UploadRvAdapter
    private lateinit var tagAdapter: TagRvAdapter

    private lateinit var immKeyboard: InputMethodManager

    private var isFeeIncluded = false
    private var lastWonTxt = ""

    private var count: EditText? = null
    private var used: Button? = null
    private var new: Button? = null
    private var canExchange: Button? = null
    private var cantExchange: Button? = null
    private var chooseFinish: Button? = null

    private lateinit var optionSheetView: View
    private lateinit var optionDialog: BottomSheetDialog

    private var fbImgList = mutableListOf<productImgUrl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hideBottomView.value = true

        binding.uploadBtnGallery.setOnClickListener(this)
        binding.uploadBtnIncludeFee.setOnClickListener(this)
        binding.uploadBtnChooseOptions.setOnClickListener(this)
        binding.uploadEdtProductContent.setOnClickListener(this)
        binding.uploadBtnUploadData.setOnClickListener(this)
        binding.uploadBtnSafepay.setOnClickListener(this)
        binding.uploadBtnCategory.setOnClickListener(this)
        binding.uploadBtnTag.setOnClickListener(this)
        binding.uploadBtnBack.setOnClickListener(this)


        setRv()
        setView()
        setDialogLogic()
        observe()

        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window,
            onShowKeyboard = {
                binding.uploadScrollView.run {
                    smoothScrollTo(scrollX, scrollY + it)
                }
            })

    }

    private fun setRv() {
        binding.uploadRvGallery.hasFixedSize()
        uploadAdapter = UploadRvAdapter(requireActivity())
        binding.uploadRvGallery.adapter = uploadAdapter

        uploadAdapter.deleteClickListener(object : UploadRvAdapter.DeleteClickListener {
            override fun onDeleteClick(view: View, position: Int) {
                viewModel.mutableUploadUriList.removeAt(position)
                viewModel.setUploadUriList()
            }

        })
    }

    private fun setView() {

        activity?.window?.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        optionSheetView = layoutInflater.inflate(R.layout.dialog_upload_choose_options, null)
        optionDialog = BottomSheetDialog(requireActivity())
        optionDialog.setContentView(optionSheetView)

        immKeyboard =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.uploadEdtProductPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true && p0.toString() != lastWonTxt) {
                    val myFormatter = DecimalFormat("###,###")
                    lastWonTxt = myFormatter.format((p0.toString().replace(",", "")).toDouble())
                    binding.uploadEdtProductPrice.setText(lastWonTxt)
                    binding.uploadEdtProductPrice.setSelection(lastWonTxt.length)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.uploadEdtProductContent.setOnFocusChangeListener { view: View, isFocus: Boolean ->
            if (isFocus && immKeyboard.isAcceptingText) {
                Log.d("0000", "setView: it is")
                binding.customView.visibility = View.VISIBLE
                binding.uploadAdditionalBottomView.visibility = View.VISIBLE
            } else {
                Log.d("0000", "setView: it isnt")
                binding.customView.visibility = View.GONE
                binding.uploadAdditionalBottomView.visibility = View.GONE
            }
        }
    }

    private fun setDialogLogic() {
        count = optionDialog.findViewById(R.id.upload_edt_product_count)
        used = optionDialog.findViewById<Button>(R.id.upload_btn_used)
        new = optionDialog.findViewById<Button>(R.id.upload_btn_new)
        cantExchange = optionDialog.findViewById(R.id.upload_btn_no_exchange)
        canExchange = optionDialog.findViewById(R.id.upload_btn_yes_exchange)
        chooseFinish = optionDialog.findViewById(R.id.upload_btn_finish_options)

        used?.setOnClickListener(this)
        new?.setOnClickListener(this)
        cantExchange?.setOnClickListener(this)
        canExchange?.setOnClickListener(this)
        chooseFinish?.setOnClickListener(this)
    }

    private fun setDialogResult() {
        if (count?.text?.isEmpty() == true) {
            viewModel.uploadProductCount.value = 1
        } else {
            viewModel.uploadProductCount.value = count?.text.toString().toInt()
        }

        optionDialog.dismiss()

        binding.uploadTxtProductCount.text = "${viewModel.uploadProductCount.value}개"
        when (viewModel.uploadProductIsNew.value) {
            true -> binding.uploadTxtProductIsUsed.text = "새상품"
            false -> binding.uploadTxtProductIsUsed.text = "중고상품"
        }
        when (viewModel.uploadProductCanExchange.value) {
            true -> binding.uploadTxtCanChange.text = "교환가능"
            false -> binding.uploadTxtCanChange.text = "교환불가"
        }

    }

    private fun observe() {
        viewModel.upLoadUriList.observe(requireActivity(), Observer {
            setNotify(it)
        })

        viewModel.uploadProductIsNew.observe(requireActivity(), Observer {
            when (it) {
                true -> {
                    new?.setBackgroundResource(R.drawable.btn_upload_choose_options_selected)
                    new?.setTextColor(requireActivity().getColor(R.color.upload_isnew_txt_selected_color))

                    used?.setBackgroundResource(R.drawable.btn_upload_choose_options)
                    used?.setTextColor(requireActivity().getColor(R.color.product_location_color))
                }
                false -> {
                    used?.setBackgroundResource(R.drawable.btn_upload_choose_options_selected)
                    used?.setTextColor(requireActivity().getColor(R.color.upload_isnew_txt_selected_color))

                    new?.setBackgroundResource(R.drawable.btn_upload_choose_options)
                    new?.setTextColor(requireActivity().getColor(R.color.product_location_color))
                }
            }
        })

        viewModel.uploadProductCanExchange.observe(requireActivity(), Observer {
            when (it) {
                true -> {
                    canExchange?.setBackgroundResource(R.drawable.btn_upload_choose_options_selected)
                    canExchange?.setTextColor(requireActivity().getColor(R.color.upload_isnew_txt_selected_color))

                    cantExchange?.setBackgroundResource(R.drawable.btn_upload_choose_options)
                    cantExchange?.setTextColor(requireActivity().getColor(R.color.product_location_color))
                }
                false -> {
                    cantExchange?.setBackgroundResource(R.drawable.btn_upload_choose_options_selected)
                    cantExchange?.setTextColor(requireActivity().getColor(R.color.upload_isnew_txt_selected_color))

                    canExchange?.setBackgroundResource(R.drawable.btn_upload_choose_options)
                    canExchange?.setTextColor(requireActivity().getColor(R.color.product_location_color))
                }
            }
        })

        viewModel.uploadIsSafePay.observe(requireActivity(), Observer {
            when (it) {
                true -> {
                    binding.uploadBtnSafepay.setBackgroundResource(R.drawable.btn_upload_safepay_clicked)
                    binding.uploadImgSafepay.setImageResource(R.drawable.icon_upload_safepay_clicked)
                }
                false -> {
                    binding.uploadBtnSafepay.setBackgroundResource(R.drawable.btn_upload_choose_options)
                    binding.uploadImgSafepay.setImageResource(R.drawable.icon_upload_safepay_unclicked)
                }
            }
        })

        viewModel.categorySelected.observe(requireActivity(), Observer {
            when (it) {
                true -> {
                    binding.uploadTxtCategoryLarge.setTextColor(
                        requireActivity().resources.getColor(
                            R.color.black
                        )
                    )
                    binding.uploadTxtCategoryLarge.text = viewModel.uploadLargeCategoryName.value
                    binding.uploadImgCategoryDivider.visibility = View.VISIBLE
                    binding.uploadTxtCategorySmall.visibility = View.VISIBLE
                    binding.uploadTxtCategorySmall.text = viewModel.uploadSmallCategoryName.value
                }
                false -> {
                    binding.uploadTxtCategoryLarge.setTextColor(
                        requireActivity().resources.getColor(
                            R.color.product_location_color
                        )
                    )
                    binding.uploadTxtCategoryLarge.text = "카테고리"
                    binding.uploadImgCategoryDivider.visibility = View.GONE
                    binding.uploadTxtCategorySmall.visibility = View.GONE
                }
            }
        })

        viewModel.uploadTagList.observe(requireActivity(), Observer {
            for (i in it) {
                Log.d("0000", "observe: $i")
            }
            Log.d("0000", "observe: ${binding.uploadTxtTag.text}")
            /*if(it[0] == "") {
                binding.uploadImgTag.visibility = View.VISIBLE
                binding.uploadTxtTag.visibility = View.VISIBLE
                binding.uploadRvTag.visibility = View.INVISIBLE
            } else if(it[0] != "") {
                binding.uploadImgTag.visibility = View.GONE
                binding.uploadTxtTag.visibility = View.GONE
                binding.uploadRvTag.visibility = View.VISIBLE

                tagAdapter = TagRvAdapter()
                binding.uploadRvTag.adapter = tagAdapter
                tagAdapter.setTagList(it)
            } else {
                Log.d("0000", "observe: 넌 뭐냐..")
            }*/
        })
    }

    private fun setNotify(list: MutableList<Uri>) {
        uploadAdapter.setList(list)
        binding.uploadTxtGallery.text = "${list.size}/12"
    }

    private fun upLoadPhotosToFirebase() {
        for (i in viewModel.mutableUploadUriList) {
            val mRef =
                ApplicationClass.fbStorage.child("Image/${viewModel.curUserData.value?.userIdx}/${i.lastPathSegment}")
            val uploadTask = mRef.putFile(i)
            //TEST
            viewModel.testImgUrl.setValue("Image/${viewModel.curUserData.value?.userIdx}/${i.lastPathSegment}")
            fbImgList.add(productImgUrl("Image/${viewModel.curUserData.value?.userIdx}/${i.lastPathSegment}"))
        }
        setUpDataToServer()
    }

    private fun setUpDataToServer() {
        UploadService(this).uploadMyProduct(
            UploadMyProductData(
                productImgList = fbImgList.toList(),
                title = binding.uploadEdtProductName.text.toString(),
                categoryLarge = viewModel.uploadLargeCategoryIdx.value!!,
                categoryMiddle = viewModel.uploadMiddleCategoryIdx.value!!,
                categorySmall = viewModel.uploadSmallCategoryIdx.value!!,
                price = (binding.uploadEdtProductPrice.text.toString().replace(",", "")).toInt(),
                productTagList = mutableListOf<tagName>(tagName("#")),
                explanation = binding.uploadEdtProductContent.text.toString(),
                shippingFee = if (isFeeIncluded) {
                    "INCLUDE"
                } else {
                    "EXCLUDE"
                },
                quantity = viewModel.uploadProductCount.value!!,
                productStatus = if (viewModel.uploadProductIsNew.value!!) {
                    "NEW"
                } else {
                    "USED"
                },
                exchangePossible = if (viewModel.uploadProductCanExchange.value!!) {
                    "EXCHANGEABLE"
                } else {
                    "NONEXCHANGEABLE"
                },
                securePayment = if (viewModel.uploadIsSafePay.value!!) {
                    "SECURE"
                } else {
                    "UNSECURE"
                }
            )
        )
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.upload_btn_gallery -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, GALLERY_REQUEST_CODE)
            }

            R.id.upload_btn_include_fee -> {
                when (isFeeIncluded) {
                    true -> {
                        binding.uploadCheckIncludeFee.setImageResource(R.drawable.icon_upload_include_fee)
                    }
                    false -> {
                        binding.uploadCheckIncludeFee.setImageResource(R.drawable.icon_upload_uninclude_fee)
                    }
                }
            }

            R.id.upload_btn_choose_options -> {
                optionDialog.show()
            }

            R.id.upload_btn_used -> viewModel.uploadProductIsNew.value = false
            R.id.upload_btn_new -> viewModel.uploadProductIsNew.value = true
            R.id.upload_btn_no_exchange -> viewModel.uploadProductCanExchange.value = false
            R.id.upload_btn_yes_exchange -> viewModel.uploadProductCanExchange.value = true

            R.id.upload_btn_finish_options -> {
                setDialogResult()
            }

            R.id.upload_btn_safepay -> {
                viewModel.safePayClicked()
            }
            R.id.upload_btn_category -> {
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                    .add(R.id.main_container, UploadCategoryChooseFragment()).commit()
            }

            R.id.upload_btn_upload_data -> {
                if (viewModel.upLoadUriList.value?.size == 0) {
                    Snackbar.make(binding.uploadScrollView, "상품 사진을 등록해주세요.", Snackbar.LENGTH_SHORT)
                        .show()
                    return
                }
                if (binding.uploadEdtProductName.text.length < 2) {
                    Snackbar.make(
                        binding.uploadScrollView,
                        "상품명을 2글자 이상 입력해주세요.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }
                if (binding.uploadEdtProductPrice.text.isEmpty() || binding.uploadEdtProductPrice.text.toString()
                        .replace(",", "").toInt() < 100
                ) {
                    Snackbar.make(
                        binding.uploadScrollView,
                        "100원 이상 입력해주세요.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                } else if (binding.uploadEdtProductPrice.text.toString().replace(",", "")
                        .toInt() < 1000 && viewModel.uploadIsSafePay.value == true
                ) {
                    Snackbar.make(
                        binding.uploadScrollView,
                        "가격이 1000원 미만인 경우 안전결제를 사용할 수 없어요.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }
                if (binding.uploadEdtProductContent.text.length < 10) {
                    Snackbar.make(
                        binding.uploadScrollView,
                        "상품설명을 10글자 이상 입력해주세요.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }
                //업로드 후 종료하기
                upLoadPhotosToFirebase()
            }

            R.id.upload_btn_tag -> {
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.main_container, UploadTagFragment()).commit()
            }

            R.id.upload_btn_back -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@UploadFragment).commit()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                val uri = data!!.data as Uri

                viewModel.mutableUploadUriList.add(uri)
                viewModel.setUploadUriList()
            }
        }
    }

    override fun onDestroyView() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroyView()
    }

    override fun onGetLargeCategorySuccess(response: UploadCategoryData) {
    }

    override fun onGetMiddleCategorySuccess(response: UploadCategoryData) {
    }

    override fun onGetSmallCategorySuccess(response: UploadCategoryData) {
    }

    override fun onUploadUserProductSuccess(response: BaseData) {
        if (response.isSuccess) {
            Log.d("0000", "onUploadUserProductSuccess: 등록 성공")
            requireActivity().supportFragmentManager.beginTransaction().remove(this@UploadFragment)
                .commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

    }
}