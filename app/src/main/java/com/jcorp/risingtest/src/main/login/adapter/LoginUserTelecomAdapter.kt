package com.jcorp.risingtest.src.main.login.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemLoginUserTelecomBinding
import com.jcorp.risingtest.src.main.login.model.LoginUserTelecomItem

class LoginUserTelecomAdapter (context : Context): RecyclerView.Adapter<LoginUserTelecomAdapter.LoginUserTelecomViewHolder>() {
    private lateinit var clickListener : ClickListener
    var mItemList = mutableListOf<LoginUserTelecomItem>()
    private val mContext = context

    interface ClickListener {
        fun onClick(view : View, position : Int)
    }
    fun clickListener (clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    inner class LoginUserTelecomViewHolder(val binding : ItemLoginUserTelecomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : LoginUserTelecomItem) {
            binding.itemLoginUserTelecomTxt.text = item.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginUserTelecomViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemLoginUserTelecomBinding>(layoutInflater, R.layout.item_login_user_telecom, parent, false)
        return LoginUserTelecomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoginUserTelecomViewHolder, position: Int) {
        holder.bind(mItemList[position])
        holder.binding.itemLoginUserTelecomTxt.text = mItemList[position].title

        if(mItemList[position].click == true) {
            holder.binding.itemLoginUserTelecomTxt.setTextColor(mContext.resources.getColor(R.color.login_user_telecom_radio_clicked))
            Glide.with(mContext)
                .load(R.drawable.icon_radio_clicked)
                .into(holder.binding.itemLoginUserTelecomBtn)
        } else {
            holder.binding.itemLoginUserTelecomTxt.setTextColor(mContext.resources.getColor(R.color.black))
            Glide.with(mContext)
                .load(R.drawable.icon_radio_unclicked)
                .into(holder.binding.itemLoginUserTelecomBtn)
        }

        holder.itemView.setOnClickListener {
            clickListener.onClick(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun setTelecomItem(list : MutableList<LoginUserTelecomItem>) {
        mItemList = list.toMutableList()
        notifyDataSetChanged()
    }
}