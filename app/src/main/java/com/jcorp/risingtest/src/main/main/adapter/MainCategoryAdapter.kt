package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemMainCategoryBinding
import com.jcorp.risingtest.src.main.main.model.HomeCategory
import com.jcorp.risingtest.src.main.main.model.MainHomeCategoryData

class MainCategoryAdapter (context : Context) : RecyclerView.Adapter<MainCategoryAdapter.MainCategoryViewHolder>() {
    var itemList = mutableListOf<HomeCategory>()
    val mContext = context

    inner class MainCategoryViewHolder(val binding : ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeCategory) {
            Glide.with(mContext)
                .load(item.iconImageUrl)
                .into(binding.itemMainCategoryImg)

            binding.itemMainCategoryTitle.text = item.categoryLargeName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemMainCategoryBinding>(layoutInflater, R.layout.item_main_category, parent, false)
        return MainCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {
        holder.bind(itemList[position])

        Glide.with(mContext)
            .load(itemList[position].iconImageUrl)
            .into(holder.binding.itemMainCategoryImg)

        holder.binding.itemMainCategoryTitle.text = itemList[position].categoryLargeName
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList(list : MutableList<HomeCategory>) {
        itemList = list
        notifyDataSetChanged()
    }
}