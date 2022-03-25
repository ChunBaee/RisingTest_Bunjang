package com.jcorp.risingtest.src.main.upload.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemUploadTagListBinding

class TagRvAdapter : RecyclerView.Adapter<TagRvAdapter.TagRvViewHolder>() {
    private var tagList = mutableListOf<String>()

    inner class TagRvViewHolder(val binding : ItemUploadTagListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String) {
            binding.itemTagTitle.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagRvViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemUploadTagListBinding>(layoutInflater, R.layout.item_upload_tag_list, parent, false)
        return TagRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagRvViewHolder, position: Int) {
        holder.bind(tagList[position])

        holder.binding.itemTagTitle.text = tagList[position]
        if(position == tagList.size -1) {
            holder.binding.itemTagDivider.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    fun setTagList (list : MutableList<String>) {
        tagList = list
        notifyDataSetChanged()
    }
}