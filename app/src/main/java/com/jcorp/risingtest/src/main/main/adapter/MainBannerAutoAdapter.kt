package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.jcorp.risingtest.R
import com.jcorp.risingtest.src.main.main.model.MainBannerData
import com.jcorp.risingtest.src.main.main.model.MainBannerResult

class MainBannerAutoAdapter (itemList : MutableList<MainBannerResult>, isInfinite : Boolean, context : Context) : LoopingPagerAdapter<MainBannerResult> (itemList.toList(), isInfinite) {
    val mContext = context
    var curPosition : Int = 0
    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        var img = convertView.findViewById<ImageView>(R.id.main_banner_image)
        Glide.with(mContext).load(itemList!![listPosition].eventImageUrl).into(img)
        curPosition = listPosition
    }

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(container.context).inflate(R.layout.item_main_banner_pager, container, false)
    }

    fun curPosition() = curPosition

}