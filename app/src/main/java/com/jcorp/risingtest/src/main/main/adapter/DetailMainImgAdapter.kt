package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.src.main.main.model.ProductImg

class DetailMainImgAdapter (itemList : MutableList<ProductImg>, isInfinite : Boolean, context : Context) : LoopingPagerAdapter<ProductImg>(itemList, isInfinite) {
    val mContext = context
    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val img = convertView.findViewById<ImageView>(R.id.product_pager_img)
        ApplicationClass.fbStorage.child(itemList!![listPosition].productImgUrl).downloadUrl.addOnCompleteListener {
            Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    a_resource: Drawable,
                    a_transition: Transition<in Drawable>?
                ) {
                    img.background = a_resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
    }

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(mContext).inflate(R.layout.item_product_detail_pager, container, false)
    }

}