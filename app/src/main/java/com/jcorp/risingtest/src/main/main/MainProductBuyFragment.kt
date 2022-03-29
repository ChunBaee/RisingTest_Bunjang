package com.jcorp.risingtest.src.main.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentBuyMainBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.main.adapter.ChooseAddrAdapter
import com.jcorp.risingtest.src.main.main.adapter.ChooseShipOptionsAdapter
import com.jcorp.risingtest.src.main.main.model.*
import com.jcorp.risingtest.src.main.main.util.MainActivityView
import com.jcorp.risingtest.src.main.main.util.MainService
import java.text.DecimalFormat

class MainProductBuyFragment(isDirect: Boolean, position: Int) :
    BaseFragment<FragmentBuyMainBinding>(FragmentBuyMainBinding::bind, R.layout.fragment_buy_main),
    MainActivityView, View.OnClickListener {

    private val viewModel by activityViewModels<MyViewModel>()
    private val isDirect = isDirect
    private val mPosition = position
    val myFormatter = DecimalFormat("###,###")

    var intPastTax = 0
    var intTax = 0
    var intTotPay = 0
    var intFinalPay = 0

    var haveLocation = false
    var isAgree = false

    var addressName: String? = null
    var addressMain: String? = null
    var addressSub: String? = null
    var addressPhone: String? = null

    var addressOption : String? = null

    lateinit var addressSheet: View
    lateinit var addressDialog: BottomSheetDialog

    lateinit var shippingOptionSheet: View
    lateinit var shippingOptionDialog: BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.hideBottomView.value = true

        getData()
        setUi()
        observe()
        setDialog()

        binding.buyBtnSetOrChangeLocation.setOnClickListener(this)
        binding.buyBtnShippingRequest.setOnClickListener(this)
        binding.buyBtnSimplepay.setOnClickListener(this)
        binding.buyBtnOtherPay.setOnClickListener(this)
        binding.buyBtnChooseOtherPayment.setOnClickListener(this)
        binding.buyBtnAgree.setOnClickListener(this)
        binding.buyTxtAgree.setOnClickListener(this)
        binding.buyBtnPay.setOnClickListener(this)

        Log.d("0000", "onViewCreated: ITS2")
        if (viewModel.choosePayment.value == true) {
            when (viewModel.curLiveChoosePayment.value) {
                0 -> binding.buyTxtOtherPay.text = "신용/체크카드"
                1 -> binding.buyTxtOtherPay.text = "카카오페이"
                2 -> binding.buyTxtOtherPay.text = "토스"
                3 -> binding.buyTxtOtherPay.text = "차이"
                4 -> binding.buyTxtOtherPay.text = "간편계좌결제"
                5 -> binding.buyTxtOtherPay.text = "휴대폰결제"
                6 -> binding.buyTxtOtherPay.text = "편의점결제"
            }
        }

    }

    private fun getData() {
        MainService(this).getBuyProductData(mPosition)
    }

    private fun setUi() {
        Glide.with(requireActivity()).load(R.raw.spinning_coin)
            .into(binding.buyDiscountSpinningCoin)

        if (isDirect) {
            binding.buyLayoutShipping.visibility = View.GONE
            binding.buyChangeable1.text = "직거래, 안전결제로"
        } else {
            binding.buyChangeable1.text = "택배거래, 안전결제로"
        }

        binding.buyEdtUsePoint.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.isNotEmpty()) {
                    viewModel.usePoint.value = p0.toString().toInt()
                } else if (p0!!.isEmpty()) {
                    viewModel.usePoint.value = 0
                }
            }

        })
    }

    private fun observe() {
        viewModel.productPrice.observe(requireActivity(), Observer {
            intPastTax = (it * 0.035).toInt()
            intTax = if (3500 > it * 0.035) 0 else (it * 0.035).toInt() - 3500
            intTotPay = it + intTax
            intFinalPay = intTotPay - viewModel.usePoint.value!!

            binding.buyPrice.text = myFormatter.format(it.toDouble())
            binding.buyPastTax.text = myFormatter.format(intPastTax)
            binding.buyTax.text = myFormatter.format(intTax)
            binding.buyTotalPrice.text = myFormatter.format(intTotPay)
            binding.buyFinalPrice.text = myFormatter.format(intTotPay - viewModel.usePoint.value!!)
        })

        viewModel.usePoint.observe(requireActivity(), Observer {
            binding.buyUsePoint.text = it.toString()
            binding.buyFinalPrice.text = myFormatter.format(intTotPay - viewModel.usePoint.value!!)
        })

        viewModel.isSimplePay.observe(requireActivity(), Observer {
            when (it) {
                true -> {
                    binding.buyImgSimplepay.setImageResource(R.drawable.img_buy_choose_other_payment)
                    binding.buyTxtSimplepay.setTextColor(requireActivity().resources.getColor(R.color.black))

                    binding.buyImgOtherPay.setImageResource(R.drawable.img_buy_not_choose_other_payment)
                    binding.buyTxtOtherPay.setTextColor(requireActivity().resources.getColor(R.color.login_start_other_text))

                    binding.buyBtnChooseOtherPayment.alpha = 0.5F
                    binding.buyBtnChooseOtherPayment.isClickable = false
                }
                false -> {
                    binding.buyImgOtherPay.setImageResource(R.drawable.img_buy_choose_other_payment)
                    binding.buyTxtOtherPay.setTextColor(requireActivity().resources.getColor(R.color.black))

                    binding.buyImgSimplepay.setImageResource(R.drawable.img_buy_not_choose_other_payment)
                    binding.buyTxtSimplepay.setTextColor(requireActivity().resources.getColor(R.color.login_start_other_text))

                    binding.buyBtnChooseOtherPayment.alpha = 1F
                    binding.buyBtnChooseOtherPayment.isClickable = true
                }
            }
        })
    }

    private fun setDialog() {
        shippingOptionSheet = layoutInflater.inflate(R.layout.dialog_shipping_options, null)
        shippingOptionDialog = BottomSheetDialog(requireActivity())
        shippingOptionDialog.setContentView(shippingOptionSheet)

        var optionRv =
            shippingOptionDialog.findViewById<RecyclerView>(R.id.dialog_shipping_options_rv)
        val optionAdapter = ChooseShipOptionsAdapter(requireActivity())
        optionRv?.adapter = optionAdapter

        optionAdapter.setOptionList(setOptionList())

        optionAdapter.clickListener(object : ChooseShipOptionsAdapter.ClickListener {
            override fun onClick(view: View, position: Int) {
                binding.buyTxtShippingRequest.text = optionAdapter.optionsList[position].title
                for (i in 0 until optionAdapter.optionsList.size) {
                    optionAdapter.optionsList[i].isClicked = false
                }
                optionAdapter.optionsList[position].isClicked = true
                addressOption = optionAdapter.optionsList[position].title
                optionAdapter.notifyDataSetChanged()
                shippingOptionDialog.dismiss()
            }
        })
    }

    private fun setOptionList(): MutableList<ShippingOptionsData> {
        var list = mutableListOf<ShippingOptionsData>()
        list.add(ShippingOptionsData("문앞", false))
        list.add(ShippingOptionsData("직접 받고 부재 시 문앞", false))
        list.add(ShippingOptionsData("경비실", false))
        list.add(ShippingOptionsData("우편함", false))
        list.add(ShippingOptionsData("직접입력", false))

        return list
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

    override fun onGetBuyProductDataSuccess(response: BuyProductData) {
        binding.buyProductPrice.text = myFormatter.format(response.result.price.toDouble())
        binding.buyProductTitle.text = response.result.title
        binding.buyShippingFee.text =
            if (response.result.shippingFee == "INCLUDE") "배송비포함" else "배송비별도"
        binding.buyTxtCurPoint.text = response.result.point.toString()

        viewModel.productPrice.value = response.result.price

        ApplicationClass.fbStorage.child(response.result.productImg).downloadUrl.addOnCompleteListener {
            Glide.with(requireActivity()).load(it.result).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    a_resource: Drawable,
                    a_transition: Transition<in Drawable>?
                ) {
                    binding.buyProductImg.background = a_resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }

        if (response.result.address == null && response.result.name == null && response.result.phone == null) {
            binding.buyShipLocationHideable.visibility = View.VISIBLE
            binding.buyTxtShipLocation.visibility = View.GONE
            binding.buyTxtShipDetailLocation.visibility = View.GONE
            binding.buyTxtShipDetailLocation2.visibility = View.GONE
            binding.buyTxtPhoneNumber.visibility = View.GONE
            binding.buyBtnSetOrChangeLocation.text = "등록"

            haveLocation = false
        } else {
            binding.buyShipLocationHideable.visibility = View.GONE
            binding.buyTxtShipLocation.visibility = View.VISIBLE
            binding.buyTxtShipDetailLocation.visibility = View.VISIBLE
            binding.buyTxtShipDetailLocation2.visibility = View.VISIBLE
            binding.buyTxtPhoneNumber.visibility = View.VISIBLE
            binding.buyBtnSetOrChangeLocation.text = "변경"

            binding.buyTxtShipLocation.text = response.result.name
            binding.buyTxtShipDetailLocation.text = response.result.address
            binding.buyTxtShipDetailLocation2.text = response.result.detailAddress
            binding.buyTxtPhoneNumber.text = response.result.phone.toString()

            addressName = response.result.name
            addressMain = response.result.address
            addressSub = response.result.detailAddress
            addressPhone = response.result.phone

            haveLocation = true
        }

    }

    override fun onPostUserAddress(response: BaseData) {
    }

    override fun onPostBuyData(response: BaseData) {
        if(response.code == 1000) {
            Log.d("0000", "onPostBuyData: 구매성공")
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_container, TradeFinishFragment()).commit()
            requireActivity().supportFragmentManager.beginTransaction().remove(this@MainProductBuyFragment).commit()
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.buy_btn_set_or_change_location -> {
                addressSheet = layoutInflater.inflate(R.layout.dialog_select_address, null)
                addressDialog = BottomSheetDialog(requireActivity())
                addressDialog.setContentView(addressSheet)
                addressDialog.show()

                if (!haveLocation && !isDirect) {
                    //택배 & 주소없음
                    addressDialog.findViewById<LinearLayout>(R.id.dialog_address_when_empty)?.visibility =
                        View.VISIBLE
                    addressDialog.findViewById<RecyclerView>(R.id.dialog_address_when_is)?.visibility =
                        View.GONE
                } else if (haveLocation && !isDirect) {
                    addressDialog.findViewById<LinearLayout>(R.id.dialog_address_when_empty)?.visibility =
                        View.VISIBLE
                    addressDialog.findViewById<RecyclerView>(R.id.dialog_address_when_is)?.visibility =
                        View.GONE

                    if (addressName != null) {
                        addressDialog.findViewById<LinearLayout>(R.id.dialog_address_when_empty)?.visibility =
                            View.GONE
                        addressDialog.findViewById<RecyclerView>(R.id.dialog_address_when_is)?.visibility =
                            View.VISIBLE
                        var rv =
                            addressDialog.findViewById<RecyclerView>(R.id.dialog_address_when_is)
                        val adapter = ChooseAddrAdapter()
                        rv?.adapter = adapter
                        val list = mutableListOf<PostUserAddrData>()
                        list.add(
                            (PostUserAddrData(
                                addressName!!,
                                addressMain!!,
                                addressSub!!,
                                addressPhone!!,
                                "MAIN"
                            ))
                        )
                        adapter.setAddrList(list)
                    }
                }

                addressDialog.findViewById<ImageView>(R.id.dialog_address_dismiss)
                    ?.setOnClickListener { addressDialog.dismiss() }
                val addBtn = addressDialog.findViewById<ImageView>(R.id.dialog_address_add)
                addBtn?.setOnClickListener {
                    addressDialog.dismiss()
                    requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(
                            R.id.main_container,
                            TradeAddAddressFragment(addressName, addressMain, addressSub, addressPhone)
                        ).commit()
                }
            }

            R.id.buy_btn_shipping_request -> {
                shippingOptionDialog.show()
            }

            R.id.buy_btn_simplepay -> {
                viewModel.isSimplePay.value = true
            }
            R.id.buy_btn_other_pay -> {
                viewModel.isSimplePay.value = false
            }
            R.id.buy_btn_choose_other_payment -> {
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.main_container, TradeChoosePayFragment()).commit()
            }
            R.id.buy_btn_agree, R.id.buy_txt_agree -> {
                if(isAgree) {
                    binding.buyBtnAgree.setImageResource(R.drawable.btn_pay_agree_unchecked)
                    isAgree = false
                }
                else {
                    binding.buyBtnAgree.setImageResource(R.drawable.btn_pay_agree_checked)
                    isAgree = true
                }
            }

            R.id.buy_btn_pay -> {
                if(isAgree) {
                    var payMethod =
                        if(viewModel.isSimplePay.value == true) {"SECURE"}
                        else {
                            when(viewModel.curLiveChoosePayment.value) {
                                0 -> "CARD"
                                1 -> "KAKAO"
                                2 -> "TOSS"
                                3 -> "CHAI"
                                4 -> "ACCOUNT"
                                5 -> "PHONE"
                                6 -> "STORE"
                                else -> ""
                            }
                        }
                    var tradingMethod = if(isDirect) "DIRECT" else "DELIVERY"
                    MainService(this).postBuyData(PostBuyData(mPosition, tradingMethod, payMethod, addressOption, binding.buyUsePoint.text.toString().toInt(), binding.buyTax.text.toString().toInt()))
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("0000", "onDestroyView: ITS")
        viewModel.choosePayment.value = true
    }
}

