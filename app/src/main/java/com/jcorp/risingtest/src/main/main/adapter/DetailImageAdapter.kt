package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.databinding.ItemProductDetailPagerBinding
import com.jcorp.risingtest.src.main.main.model.ProductImg


class DetailImageAdapter(context : Context) : RecyclerView.Adapter<DetailImageAdapter.DetailImageViewHolder>() {
    private var imgList = mutableListOf<ProductImg>()
    private val mContext = context

    inner class DetailImageViewHolder(val binding : ItemProductDetailPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductImg) {
            ApplicationClass.fbStorage.child(item.productImgUrl).downloadUrl.addOnCompleteListener {
                Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        a_resource: Drawable,
                        a_transition: Transition<in Drawable>?
                    ) {
                        binding.productPagerImg.background = a_resource
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemProductDetailPagerBinding>(layoutInflater, R.layout.item_product_detail_pager, parent, false)
        return DetailImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        holder.bind(imgList[position])

        ApplicationClass.fbStorage.child(imgList[position].productImgUrl).downloadUrl.addOnCompleteListener {
            Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    a_resource: Drawable,
                    a_transition: Transition<in Drawable>?
                ) {
                    holder.binding.productPagerImg.background = a_resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return imgList.size
    }
}