package com.jcorp.risingtest.src.main.login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.jcorp.risingtest.R

class LoginAutoPagerAdapter(itemList : MutableList<Int>, isInfinite : Boolean) : LoopingPagerAdapter<Int>(itemList, isInfinite){
    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        convertView.findViewById<View>(R.id.item_login_pager_img).setBackgroundResource(itemList!![listPosition])
    }

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(container.context).inflate(R.layout.item_login_start_pager, container, false)
    }
}