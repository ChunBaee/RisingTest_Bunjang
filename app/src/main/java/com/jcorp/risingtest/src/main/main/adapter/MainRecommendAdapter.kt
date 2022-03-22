package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemMainRecommendBinding
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem

class MainRecommendAdapter (context : Context) : RecyclerView.Adapter<MainRecommendAdapter.MainRecommendViewHolder>(){
    private var itemList = mutableListOf<MainRecommendRvItem>()
    private val mContext = context

    inner class MainRecommendViewHolder(val binding : ItemMainRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : MainRecommendRvItem) {
            Glide.with(mContext)
                .load(item.productPhoto)
                .into(binding.itemMainRecommendPhoto)

            binding.itemMainRecommendPrice.text = item.price
            if(item.isThunderPay == true) {
                //안전 글자 추가
            } else if (item.isThunderPay == false) {
                binding.itemMainRecommendProductName.text = item.productName
                binding.itemMainRecommendIsThunderPay.visibility = View.GONE
            }
            binding.itemMainRecommendLocation.text = item.location
            if(item.heartCount != 0) {
                binding.itemMainRecommendHeartImg.visibility = View.VISIBLE
                binding.itemMainRecommendHeartTxt.text = item.heartCount.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecommendViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemMainRecommendBinding>(layoutInflater, R.layout.item_main_recommend, parent, false)
        return MainRecommendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainRecommendViewHolder, position: Int) {
        holder.bind(itemList[position])

        Glide.with(mContext)
            .load(itemList[position].productPhoto)
            .into(holder.binding.itemMainRecommendPhoto)

        holder.binding.itemMainRecommendPrice.text = itemList[position].price
        if(itemList[position].isThunderPay == true) {
            //안전 글자 추가
        } else if (itemList[position].isThunderPay == false) {
            holder.binding.itemMainRecommendProductName.text = itemList[position].productName
            holder.binding.itemMainRecommendIsThunderPay.visibility = View.GONE
        }
        holder.binding.itemMainRecommendLocation.text = itemList[position].location
        if(itemList[position].heartCount != 0) {
            holder.binding.itemMainRecommendHeartImg.visibility = View.VISIBLE
            holder.binding.itemMainRecommendHeartTxt.text = itemList[position].heartCount.toString()
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setRecommendList(list : MutableList<MainRecommendRvItem>) {
        itemList = list.toMutableList()
        notifyDataSetChanged()
    }
}