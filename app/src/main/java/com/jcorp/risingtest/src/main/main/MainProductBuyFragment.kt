package com.jcorp.risingtest.src.main.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentBuyMainBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.main.model.*
import com.jcorp.risingtest.src.main.main.util.MainActivityView
import com.jcorp.risingtest.src.main.main.util.MainService
import java.text.DecimalFormat

class MainProductBuyFragment (isDirect : Boolean, position : Int) : BaseFragment<FragmentBuyMainBinding>(FragmentBuyMainBinding::bind, R.layout.fragment_buy_main),
    MainActivityView {

    private val viewModel by activityViewModels<MyViewModel>()
    private val isDirect = isDirect
    private val mPosition = position
    val myFormatter = DecimalFormat("###,###")

    var intPastTax = 0
    var intTax = 0
    var intTotPay = 0
    var intFinalPay = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setUi()
        observe()

    }

    private fun getData() {
        MainService(this).getBuyProductData(mPosition)
    }

    private fun setUi() {
        Glide.with(requireActivity()).load(R.raw.spinning_coin).into(binding.buyDiscountSpinningCoin)

        if(isDirect) {
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
                if(p0!!.isNotEmpty()) {
                    viewModel.usePoint.value = p0.toString().toInt()
                } else if(p0!!.isEmpty()) {
                    viewModel.usePoint.value = 0
                }
            }

        })

    }

    private fun observe() {
        viewModel.productPrice.observe(requireActivity(), Observer {
            intPastTax = (it * 0.035).toInt()
            intTax = if(3500 > it*0.035) 0 else (it * 0.035).toInt() - 3500
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
        binding.buyShippingFee.text = if(response.result.shippingFee == "INCLUDE") "배송비포함" else "배송비별도"
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

        if(response.result.address == null && response.result.name == null && response.result.phone == null) {
            binding.buyShipLocationHideable.visibility = View.VISIBLE
            binding.buyTxtShipLocation.visibility = View.GONE
            binding.buyTxtShipDetailLocation.visibility = View.GONE
            binding.buyTxtShipDetailLocation2.visibility = View.GONE
            binding.buyTxtPhoneNumber.visibility = View.GONE
            binding.buyBtnSetOrChangeLocation.text = "등록"
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
            binding.buyTxtPhoneNumber.text = response.result.phone
        }

    }
}