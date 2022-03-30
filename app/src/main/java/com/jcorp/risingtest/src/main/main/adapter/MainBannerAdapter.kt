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
import com.jcorp.risingtest.databinding.ItemMainBannerPagerBinding
import com.jcorp.risingtest.src.main.main.model.MainBannerResult

class MainBannerAdapter(context : Context) : RecyclerView.Adapter<MainBannerAdapter.MainBannerViewHolder>() {
    var bannerList = mutableListOf<MainBannerResult>()
    private val mContext = context

    inner class MainBannerViewHolder(val binding : ItemMainBannerPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : MainBannerResult) {
            Glide.with(mContext).load(item.eventImageUrl).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(a_resource: Drawable, a_transition: Transition<in Drawable>?) {
                    binding.mainBannerImage.background = a_resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBannerViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemMainBannerPagerBinding>(inflater, R.layout.item_main_banner_pager, parent, false)
        return MainBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainBannerViewHolder, position: Int) {
        holder.bind(bannerList[position])

        Glide.with(mContext).load(bannerList[position].eventImageUrl).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(a_resource: Drawable, a_transition: Transition<in Drawable>?) {
                holder.binding.mainBannerImage.background = a_resource
            }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    fun setBannerLists(list : MutableList<MainBannerResult>) {
        bannerList = list.toMutableList()
        notifyDataSetChanged()
    }
}