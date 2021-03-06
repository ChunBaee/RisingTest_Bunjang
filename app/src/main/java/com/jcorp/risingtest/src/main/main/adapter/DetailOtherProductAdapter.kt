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
import com.jcorp.risingtest.databinding.ItemProductDetailOtherProductBinding
import com.jcorp.risingtest.src.main.main.model.SellProduct
import java.text.DecimalFormat

class DetailOtherProductAdapter (context : Context) : RecyclerView.Adapter<DetailOtherProductAdapter.DetailOtherProductViewHolder>() {
    private var productList = mutableListOf<SellProduct>()
    private val mContext = context
    val myFormatter = DecimalFormat("###,###")

    inner class DetailOtherProductViewHolder(val binding : ItemProductDetailOtherProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : SellProduct) {

            ApplicationClass.fbStorage.child(item.productImgUrl).downloadUrl.addOnCompleteListener {
                Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        a_resource: Drawable,
                        a_transition: Transition<in Drawable>?
                    ) {
                        binding.detailOtherProductImg.background = a_resource
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
            }
            binding.detailPriceTxt.text = myFormatter.format(item.price.toDouble())
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailOtherProductViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemProductDetailOtherProductBinding>(layoutInflater, R.layout.item_product_detail_other_product, parent, false)
        return DetailOtherProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailOtherProductViewHolder, position: Int) {
        holder.bind(productList[position])

        ApplicationClass.fbStorage.child(productList[position].productImgUrl).downloadUrl.addOnCompleteListener {
            Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    a_resource: Drawable,
                    a_transition: Transition<in Drawable>?
                ) {
                    holder.binding.detailOtherProductImg.background = a_resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
        holder.binding.detailPriceTxt.text = myFormatter.format(productList[position].price.toDouble())
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setOtherProductList(list : MutableList<SellProduct>) {
        productList = list.toMutableList()
        notifyDataSetChanged()
    }
}