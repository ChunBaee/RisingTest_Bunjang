package com.jcorp.risingtest.src.main.upload.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemUploadGalleryImageBinding

class UploadRvAdapter(context : Context) : RecyclerView.Adapter<UploadRvAdapter.UploadRvViewHolder>() {
    private var photoList = mutableListOf<Uri>()
    private val mContext = context
    private lateinit var deleteClickListener : DeleteClickListener

    interface DeleteClickListener {
        fun onDeleteClick (view : View, position : Int)
    }

    fun deleteClickListener (deleteClickListener: DeleteClickListener) {
        this.deleteClickListener = deleteClickListener
    }

    inner class UploadRvViewHolder(val binding : ItemUploadGalleryImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Uri) {
            Glide.with(mContext)
                .load(item)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(a_resource: Drawable, a_transition: Transition<in Drawable>?) {
                        binding.itemUploadGalleryImg.background = a_resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            binding.itemUploadGalleryDelete.bringToFront()
            binding.itemUploadGalleryDelete.bringToFront()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadRvViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemUploadGalleryImageBinding>(layoutInflater, R.layout.item_upload_gallery_image,parent, false)
        return UploadRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UploadRvViewHolder, position: Int) {
        holder.bind(photoList[position])

        Glide.with(mContext)
            .load(photoList[position])
            .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(a_resource: Drawable, a_transition: Transition<in Drawable>?) {
                holder.binding.itemUploadGalleryImg.background = a_resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
        holder.binding.itemUploadGalleryDelete.bringToFront()
        holder.binding.itemUploadGalleryDelete.bringToFront()

        holder.binding.itemUploadGalleryDelete.setOnClickListener {
            deleteClickListener.onDeleteClick(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun setList (list : MutableList<Uri>) {
        photoList = list.toMutableList()
        notifyDataSetChanged()
    }
}