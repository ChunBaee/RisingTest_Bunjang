package com.jcorp.risingtest.src.main.upload.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemUploadTagListBinding
import com.jcorp.risingtest.src.main.upload.model.tagName

class TagRvAdapter : RecyclerView.Adapter<TagRvAdapter.TagRvViewHolder>() {
    var tagsList = mutableListOf<tagName>()

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
        holder.bind(tagsList[position].tagName)

        holder.binding.itemTagTitle.text = tagsList[position].tagName
        if(position == tagsList.size -1) {
            holder.binding.itemTagDivider.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return tagsList.size
    }

    fun setTagList (list : MutableList<String?>) {
        tagsList = mutableListOf<tagName>()
        for(i in 0 until list.size) {
            if(list[i] != null) {
                tagsList.add(tagName(list[i]!!))
            }
        }
        notifyDataSetChanged()
    }
}