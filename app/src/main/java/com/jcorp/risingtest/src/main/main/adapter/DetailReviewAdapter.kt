package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.databinding.ItemProductDetailReviewBinding
import com.jcorp.risingtest.src.main.main.model.ReviewProduct

class DetailReviewAdapter(context : Context) : RecyclerView.Adapter<DetailReviewAdapter.DetailReviewViewHolder>() {

    private var reviewList = mutableListOf<ReviewProduct>()
    private val mContext = context

    inner class DetailReviewViewHolder (val binding : ItemProductDetailReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ReviewProduct) {
            ApplicationClass.fbStorage.child(item.profileUrl).downloadUrl.addOnCompleteListener {
                Glide.with(mContext).load(it.result).into(binding.reviewerProfilePhoto)
            }
            binding.reviewerProfileName.text = item.storeName
            binding.reviewProductName.text = item.title
            if(item.securePayment == "SECURE") {
                binding.iconWasThunderPay.visibility = View.VISIBLE
            } else {
                binding.iconWasThunderPay.visibility = View.GONE
            }
            binding.reviewProductRating.rating = item.rate.toFloat()
            binding.reviewProductContent.text = item.explanation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemProductDetailReviewBinding>(layoutInflater, R.layout.item_product_detail_review, parent, false)
        return DetailReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        holder.bind(reviewList[position])

        ApplicationClass.fbStorage.child(reviewList[position].profileUrl).downloadUrl.addOnCompleteListener {
            Glide.with(mContext).load(it.result).into(holder.binding.reviewerProfilePhoto)
        }
        holder.binding.reviewerProfileName.text = reviewList[position].storeName
        holder.binding.reviewProductName.text = reviewList[position].title
        if(reviewList[position].securePayment == "SECURE") {
            holder.binding.iconWasThunderPay.visibility = View.VISIBLE
        } else {
            holder.binding.iconWasThunderPay.visibility = View.GONE
        }
        holder.binding.reviewProductRating.rating = reviewList[position].rate.toFloat()
        holder.binding.reviewProductContent.text = reviewList[position].explanation

    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    fun setReviewList(list : MutableList<ReviewProduct>) {
        reviewList = list.toMutableList()
        notifyDataSetChanged()
    }
}