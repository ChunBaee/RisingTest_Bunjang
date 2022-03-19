package com.jcorp.risingtest.src.main.login.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemLoginStartPagerBinding

class LoginPagerAdapter (imgList : MutableList<Int>, context : Context) : RecyclerView.Adapter<LoginPagerAdapter.LoginPagerViewHolder>() {
    private var mImgList = imgList
    private var mContext = context

    inner class LoginPagerViewHolder(val binding : ItemLoginStartPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (img : Int) {
            Glide.with(mContext)
                .load(img)
                .into(binding.itemLoginPagerImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginPagerViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemLoginStartPagerBinding>(layoutInflater, R.layout.item_login_start_pager, parent, false)

        return LoginPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoginPagerViewHolder, position: Int) {
        holder.bind(mImgList[position%4])

        Glide.with(mContext)
            .load(mImgList[position%4])
            .into(holder.binding.itemLoginPagerImg)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

}