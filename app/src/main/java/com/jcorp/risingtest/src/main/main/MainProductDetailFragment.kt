package com.jcorp.risingtest.src.main.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentMainItemDetailBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.main.adapter.*
import com.jcorp.risingtest.src.main.main.model.*
import com.jcorp.risingtest.src.main.main.util.MainActivityView
import com.jcorp.risingtest.src.main.main.util.MainService
import java.text.DecimalFormat

class MainProductDetailFragment (position : Int) : BaseFragment<FragmentMainItemDetailBinding>(FragmentMainItemDetailBinding::bind, R.layout.fragment_main_item_detail), MainActivityView, View.OnClickListener {
    private val viewModel by activityViewModels<MyViewModel>()

    private lateinit var photoAdapter : DetailMainImgAdapter
    private lateinit var tagAdapter : DetailTagAdapter
    private lateinit var otherProductAdapter : DetailOtherProductAdapter
    private lateinit var reviewAdapter : DetailReviewAdapter
    private lateinit var similarAdapter : DetailSimilarAdapter

    private val mPosition = position
    private var likeState = false

    private val myFormatter = DecimalFormat("###,###")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hideBottomView.value = true

        binding.productDetailBtnBack.setOnClickListener(this)
        binding.productDetailBtnThunderPay.setOnClickListener(this)
        binding.productDetailBtnZzim.setOnClickListener(this)

        getData()
        observe()
        setToolbarAndHiddenView()

        setImgPager()
        setTagList()
        setOtherProductList()
        setReviewList()
        setSimilarList()

    }

    private fun getData() {
        MainService(this).getProductDetailData(mPosition)
    }

    private fun observe() {
        viewModel.isDirect.observe(requireActivity(), Observer {
            when(it) {
                true -> {
                    //직거래창
                }
                false -> {
                    //택배거래창
                }
            }
        })
    }

    private fun setToolbarAndHiddenView() {
        binding.productDetailToolbar.background.alpha = 0
        binding.productDetailToolbarTitle.alpha = 0F
        binding.productDetailSmallLayout.alpha = 0F

        binding.productDetailScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.d("0000", "setToolbarAndHiddenView: $scrollY")

            binding.productDetailToolbar.background.alpha = (scrollY * 0.2742).toInt()
            binding.productDetailToolbarTitle.alpha = ((1.0/930.0).toFloat() * scrollY)

            if(scrollY > 930) {
                binding.productDetailToolbar.background.alpha = 255
                if(scrollY > 1000) {
                    binding.productDetailSmallLayout.alpha = ((scrollY - 1000.0)/500.0).toFloat()
                } else if(scrollY <= 1000) {
                    binding.productDetailSmallLayout.alpha = 0F
                }
            }

            if(scrollY > 465) {
                binding.productDetailBtnBack.background.alpha = ((scrollY - 465) * (255.0/465.0)).toInt()
                binding.productDetailBtnShare.background.alpha = ((scrollY - 465) * (255.0/465.0)).toInt()
                binding.productDetailBtnSearch.background.alpha = ((scrollY - 465) * (255.0/465.0)).toInt()
                binding.productDetailBtnBack.background.setTint(requireActivity().resources.getColor(R.color.black))
                binding.productDetailBtnShare.background.setTint(requireActivity().resources.getColor(R.color.black))
                binding.productDetailBtnSearch.background.setTint(requireActivity().resources.getColor(R.color.black))
            } else if(scrollY < 465) {
                binding.productDetailBtnBack.background.alpha = 255 - ((255/465)* scrollY)
                binding.productDetailBtnShare.background.alpha = 255 - ((255/465)* scrollY)
                binding.productDetailBtnSearch.background.alpha = 255 - ((255/465)* scrollY)
                binding.productDetailBtnBack.background.setTint(requireActivity().resources.getColor(R.color.white))
                binding.productDetailBtnShare.background.setTint(requireActivity().resources.getColor(R.color.white))
                binding.productDetailBtnSearch.background.setTint(requireActivity().resources.getColor(R.color.white))
            }


        }
    }

    private fun setImgPager() {

    }

    private fun setTagList() {
        tagAdapter = DetailTagAdapter()
        binding.productDetailTagRv.hasFixedSize()
        binding.productDetailTagRv.adapter = tagAdapter
    }

    private fun setOtherProductList() {
        otherProductAdapter = DetailOtherProductAdapter(requireActivity())
        binding.productDetailOtherProductRv.hasFixedSize()
        binding.productDetailOtherProductRv.adapter = otherProductAdapter
    }

    private fun setReviewList() {
        reviewAdapter = DetailReviewAdapter(requireActivity())
        binding.productDetailReviewRv.hasFixedSize()
        binding.productDetailReviewRv.adapter = reviewAdapter
    }

    private fun setSimilarList() {
        similarAdapter = DetailSimilarAdapter(requireActivity())
        binding.productDetailSimilarRv.hasFixedSize()
        binding.productDetailSimilarRv.adapter = similarAdapter
    }

    override fun onPause() {
        viewModel.hideBottomView.value = false
        super.onPause()
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
        photoAdapter = DetailMainImgAdapter(response.result.productImgList.toMutableList(),false,requireActivity())
        binding.productDetailPager.adapter = photoAdapter
        binding.productDetailPagerIndicator.attachViewPager(binding.productDetailPager)
        binding.productDetailPagerIndicator.setDotTint(requireActivity().resources.getColor(R.color.product_detail_thunderpay_color))

        tagAdapter.setTagList(response.result.productTagList.toMutableList())
        otherProductAdapter.setOtherProductList(response.result.sellProductList.toMutableList())
        reviewAdapter.setReviewList(response.result.reviewList.toMutableList())
        similarAdapter.setSimilarList(response.result.relateProductList.toMutableList())

        binding.productDetailPrice.text = myFormatter.format(response.result.productInfo.price.toDouble())
        binding.productDetailTitle.text = response.result.productInfo.title
        binding.productDetailTxtTime.text = response.result.productInfo.createdAt
        binding.productDetailTxtZzim.text = response.result.productInfo.favoriteCount
        binding.productDetailTxtWatch.text = response.result.productInfo.viewCount.toString()
        binding.productDetailLocationTxt.text = response.result.productInfo.directAddress
        binding.productDetailMainContent.text = response.result.productInfo.explanation
        binding.productDetailTxtCategory.text = response.result.productInfo.category
        binding.productDetailTxtUsersInquiry.text = response.result.productInfo.productInquiry
        binding.productDetailIsUsedTxt.text = response.result.productInfo.productStatus
        binding.productDetailIncludeFeeTxt.text = response.result.productInfo.shippingFee
        binding.productDetailProductCountTxt.text = "${response.result.productInfo.quantity}개"

        binding.productDetailShopName.text = response.result.storeInfo.storeName
        binding.productDetailShopFollowCount.text = response.result.storeInfo.followerCount.toString()
        binding.productDetailShopRating.rating = response.result.storeInfo.starRate.toFloat()

        binding.productDetailShopProductCount.text = response.result.sellProductList.size.toString()
        binding.productDetailShopReviewCount.text = response.result.reviewList.size.toString()
        binding.productDetailReviewRating.rating = response.result.storeInfo.starRate.toFloat()


        binding.productDetailSmallToolbarTitle.text = response.result.productInfo.title
        binding.productDetailSmallToolbarPrice.text = myFormatter.format(response.result.productInfo.price.toDouble())
        binding.productDetailSmallShopName.text = response.result.storeInfo.storeName
        binding.productDetailSmallRatingbar.rating = response.result.storeInfo.starRate.toFloat()
        binding.productDetailSmallRatingTxt.text = response.result.storeInfo.starRate.toFloat().toString()
        binding.productDetailSmallReviewCount.text = "(${response.result.storeInfo.reviewCount})"
        binding.productDetailSmallShippingFee.text = response.result.productInfo.shippingFee

        ApplicationClass.fbStorage.child(response.result.productImgList[0].productImgUrl).downloadUrl.addOnCompleteListener {
            Glide.with(requireActivity()).load(it.result).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    a_resource: Drawable,
                    a_transition: Transition<in Drawable>?
                ) {
                    binding.productDetailSmallToolbarPhoto.background = a_resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }

        if(response.result.productInfo.myFavorite == "LIKE") {
            binding.productDetailHeart.setImageResource(R.drawable.icon_heart_filled)
            likeState = true
        } else if(response.result.productInfo.myFavorite == "UNLIKE") {
            binding.productDetailHeart.setImageResource(R.drawable.icon_product_detail_heart)
            likeState = false
        }
    }

    override fun onPostUserFavoriteSuccess(response: BaseData) {
    }

    override fun onGetBuyProductDataSuccess(response: BuyProductData) {
    }

    override fun onPostUserAddress(response: BaseData) {
    }

    override fun onPostBuyData(response: BaseData) {
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.product_detail_btn_back -> {
                requireActivity().supportFragmentManager.beginTransaction().remove(this@MainProductDetailFragment).commit()
                requireActivity().supportFragmentManager.popBackStack()
            }

            R.id.product_detail_btn_thunder_pay -> {
                val paySheetView = layoutInflater.inflate(R.layout.dialog_detail_order_product, null)
                val payDialog = BottomSheetDialog(requireActivity())
                payDialog.setContentView(paySheetView)
                payDialog.show()

                val dismissBtn = payDialog.findViewById<ImageView>(R.id.dialog_pay_dismiss_btn)
                val shippingBtn = payDialog.findViewById<ConstraintLayout>(R.id.dialog_pay_shipping_btn)
                val directBtn = payDialog.findViewById<ConstraintLayout>(R.id.dialog_pay_direct_btn)

                dismissBtn?.setOnClickListener {
                    payDialog.dismiss()
                }
                shippingBtn?.setOnClickListener {
                    viewModel.isDirect.value = false
                    payDialog.dismiss()
                    requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.main_container, MainProductBuyFragment(false, mPosition)).commit()
                }
                directBtn?.setOnClickListener {
                    viewModel.isDirect.value = true
                    payDialog.dismiss()
                    requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.main_container, MainProductBuyFragment(true, mPosition)).commit()
                }
            }

            R.id.product_detail_btn_zzim -> {
                if(likeState) {
                    binding.productDetailHeart.setImageResource(R.drawable.icon_product_detail_heart)
                    binding.productDetailTxtZzim.text = (binding.productDetailTxtZzim.text.toString().toInt() - 1).toString()
                    MainService(this).postUserFavoriteData(mPosition)
                    Snackbar.make(binding.productDetailScrollView, "찜 해제가 완료되었습니다", Snackbar.LENGTH_SHORT).show()
                    likeState = false
                } else {
                    binding.productDetailHeart.setImageResource(R.drawable.icon_heart_filled)
                    binding.productDetailTxtZzim.text = (binding.productDetailTxtZzim.text.toString().toInt() + 1).toString()
                    MainService(this).postUserFavoriteData(mPosition)
                    Snackbar.make(binding.productDetailScrollView, "찜 목록에 추가했어요!", Snackbar.LENGTH_SHORT).show()
                    likeState = true
                }
            }
        }
    }

}