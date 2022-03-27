package com.jcorp.risingtest.src.main.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemMainRecommendBinding
import com.jcorp.risingtest.databinding.ItemProductDetailTagBinding
import com.jcorp.risingtest.src.main.main.model.ProductTag

class DetailTagAdapter : RecyclerView.Adapter<DetailTagAdapter.DetailTagViewHolder>() {
    private var tagList = mutableListOf<ProductTag>()

    inner class DetailTagViewHolder(val binding : ItemProductDetailTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : ProductTag) {
            binding.productDetailTxtTag.text = item.tagName.replace("#","")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTagViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemProductDetailTagBinding>(layoutInflater, R.layout.item_product_detail_tag, parent, false)
        return DetailTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailTagViewHolder, position: Int) {
        holder.bind(tagList[position])
        holder.binding.productDetailTxtTag.text = tagList[position].tagName.replace("#","")
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    fun setTagList(list : MutableList<ProductTag>) {
        tagList = list.toMutableList()
        notifyDataSetChanged()
    }
}