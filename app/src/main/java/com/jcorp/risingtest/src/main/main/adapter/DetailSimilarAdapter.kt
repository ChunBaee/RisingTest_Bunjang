package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.databinding.ItemProductDetailSimilarProductBinding
import com.jcorp.risingtest.src.main.main.model.RelateProduct
import java.text.DecimalFormat

class DetailSimilarAdapter (context : Context): RecyclerView.Adapter<DetailSimilarAdapter.DetailSimilarViewHolder>() {

    private var similarList = mutableListOf<RelateProduct>()
    private val mContext = context
    val myFormatter = DecimalFormat("###,###")

    inner class DetailSimilarViewHolder(val binding : ItemProductDetailSimilarProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : RelateProduct) {
            ApplicationClass.fbStorage.child(item.productImgUrl).downloadUrl.addOnCompleteListener {
                Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        a_resource: Drawable,
                        a_transition: Transition<in Drawable>?
                    ) {
                        binding.productOtherProductPhoto.background = a_resource
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
            }
            binding.productOtherProductPrice.text = myFormatter.format(item.price.toDouble())
            binding.productOtherProductName.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailSimilarViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemProductDetailSimilarProductBinding>(layoutInflater, R.layout.item_product_detail_similar_product, parent, false)
        return DetailSimilarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailSimilarViewHolder, position: Int) {
        holder.bind(similarList[position])

        ApplicationClass.fbStorage.child(similarList[position].productImgUrl).downloadUrl.addOnCompleteListener {
            Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    a_resource: Drawable,
                    a_transition: Transition<in Drawable>?
                ) {
                    holder.binding.productOtherProductPhoto.background = a_resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }

        holder.binding.productOtherProductPrice.text = myFormatter.format(similarList[position].price.toDouble())
        holder.binding.productOtherProductName.text = similarList[position].title

    }

    override fun getItemCount(): Int {
        return similarList.size
    }

    fun setSimilarList(list : MutableList<RelateProduct>) {
        similarList = list.toMutableList()
        notifyDataSetChanged()
    }
}