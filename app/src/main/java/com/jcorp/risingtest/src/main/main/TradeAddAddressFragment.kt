package com.jcorp.risingtest.src.main.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentAddAddressBinding
import com.jcorp.risingtest.src.main.main.adapter.AddAddrAdapter
import com.jcorp.risingtest.src.main.main.model.*
import com.jcorp.risingtest.src.main.main.util.MainActivityView
import com.jcorp.risingtest.src.main.main.util.MainService
import com.jcorp.risingtest.src.main.upload.adapter.UploadRvAdapter

class TradeAddAddressFragment (name : String?, main : String?, sub : String?, phone : String?): BaseFragment<FragmentAddAddressBinding>(FragmentAddAddressBinding::bind, R.layout.fragment_add_address),
    View.OnClickListener, MainActivityView {
    var reqName = name
    var reqMain = main
    var reqSub = sub
    var reqPhone = phone

    lateinit var inputName : String
    var inputPhone : Int = 0
    lateinit var inputAddr : String
    lateinit var inputDetAddr : String

    var isName = false
    var isPhone = false
    var isAddr = false
    var isDetAddr = false
    var isMain = false

    private var addrList = mutableListOf<PostUserAddrData>()
    private lateinit var addrAdapter : AddAddrAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkReq()

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        binding.addAddressBtnAdd.setOnClickListener(this)
        binding.addAddressIsMain.setOnClickListener(this)
        binding.addAddressBtnSet.setOnClickListener(this)

        textListener()
    }

    private fun checkReq() {
        if(reqName != null) {
            binding.addAddressLayoutWhenEmpty.visibility = View.GONE
        } else {
            binding.addAddressRv.visibility = View.VISIBLE
        }
    }

    private fun textListener() {
        binding.addAddressName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                inputName = p0.toString()
                isName = p0!!.isNotEmpty()
            }
        })
        binding.addAddressPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                inputPhone = p0.toString().toInt()
                isPhone = p0!!.isNotEmpty()
            }
        })
        binding.addAddressAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                inputAddr = p0.toString()
                isAddr = p0!!.isNotEmpty()
            }
        })
        binding.addAddressDetailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                inputDetAddr = p0.toString()
                isDetAddr = p0!!.isNotEmpty()
                if(isDetAddr) {
                    binding.addAddressBtnSet.alpha = 1F
                    binding.addAddressBtnSet.isClickable = true
                }
            }
        })
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.add_address_btn_add -> {
                binding.addAddressLayoutWhenEmpty.visibility = View.GONE
                binding.addAddressRv.visibility = View.GONE
                binding.addAddressLayoutWhenAdd.visibility = View.VISIBLE
                binding.addAddressBtnSet.visibility = View.VISIBLE
            }
            R.id.add_address_is_main -> {
                when(isMain) {
                    true -> {
                        binding.addAddressImgIsMain.setImageResource(R.drawable.btn_pay_agree_unchecked)
                        binding.addAddressTxtIsMain.setTextColor(requireActivity().resources.getColor(R.color.login_start_other_text))
                        isMain = false
                    }
                    false -> {
                        binding.addAddressImgIsMain.setImageResource(R.drawable.btn_pay_agree_checked)
                        binding.addAddressTxtIsMain.setTextColor(requireActivity().resources.getColor(R.color.black))
                        isMain = true
                    }
                }
            }

            R.id.add_address_btn_set -> {
                if(isName && isPhone && isAddr && isDetAddr) {
                    MainService(this).postUserAddrData(PostUserAddrData(inputName, inputPhone.toString(), inputAddr, inputDetAddr, if(isMain) "MAIN" else "SUB"))
                }
            }

        }
    }

    override fun onGetDataSuccess(response: CurUserData) {
    }

    override fun onHomeBannerDataSuccess(response: MainBannerData) {
    }

    override fun onHomeCategoryDataSuccess(response: HomeCategoryData) {
    }

    override fun onRecommendDataSuccess(response: MainRecommendRvItem) {
    }

    override fun onProductDetailDataSuccess(response: ProductDetailData) {
    }

    override fun onPostUserFavoriteSuccess(response: BaseData) {
    }

    override fun onGetBuyProductDataSuccess(response: BuyProductData) {
    }

    override fun onPostUserAddress(response: BaseData) {
        if(response.code == 1000) {
            Snackbar.make(binding.addAddressToolbar, "주소가 추가되었습니다", Snackbar.LENGTH_SHORT).show()

            binding.addAddressLayoutWhenAdd.visibility = View.GONE
            binding.addAddressLayoutWhenEmpty.visibility = View.GONE
            binding.addAddressRv.visibility = View.VISIBLE

            binding.addAddressRv.hasFixedSize()
            addrAdapter = AddAddrAdapter()
            binding.addAddressRv.adapter = addrAdapter

            addrList.add(PostUserAddrData(inputName, inputPhone.toString(), inputAddr, inputDetAddr, ""))
            addrAdapter.setAddrList(addrList)

            addrAdapter.deleteListener(object : AddAddrAdapter.DeleteListener {
                override fun deleteClick(view: View, position: Int) {
                    Log.d("0000", "deleteClick: $position")
                }
            })
            binding.addAddressBtnSet.visibility = View.GONE
        }
    }

    override fun onPostBuyData(response: BaseData) {
    }
}