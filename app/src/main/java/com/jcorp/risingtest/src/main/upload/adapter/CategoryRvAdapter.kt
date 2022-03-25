package com.jcorp.risingtest.src.main.upload.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemUploadCategoryFormBinding
import com.jcorp.risingtest.src.main.upload.model.CategoryData
import okhttp3.internal.notify

class CategoryRvAdapter (context : Context) : RecyclerView.Adapter<CategoryRvAdapter.CategoryRvViewHolder>() {
    private lateinit var categoryClickListener : CategoryClickListener
    private val mContext = context
    private var mList = mutableListOf<CategoryData>()

    interface CategoryClickListener {
        fun onClick (view : View, position : Int, name : String)
    }
    fun categoryClickListener (categoryClickListener: CategoryClickListener) {
        this.categoryClickListener = categoryClickListener
    }

    inner class CategoryRvViewHolder(val binding : ItemUploadCategoryFormBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CategoryData) {
            Glide.with(mContext).load(item.categoryUrl).into(binding.categoryImg)
            binding.categoryTitle.text = item.categoryName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRvViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemUploadCategoryFormBinding>(layoutInflater, R.layout.item_upload_category_form, parent, false)
        return CategoryRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryRvViewHolder, position: Int) {
        holder.bind(mList[position])

        Glide.with(mContext).load(mList[position].categoryUrl).into(holder.binding.categoryImg)
        holder.binding.categoryTitle.text = mList[position].categoryName

        holder.itemView.setOnClickListener {
            categoryClickListener.onClick(it, mList[position].categoryIdx, mList[position].categoryName)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData (list : MutableList<CategoryData>) {
        mList = list.toMutableList()
        notifyDataSetChanged()
    }
}