package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.databinding.ItemMainRecommendBinding
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem
import com.jcorp.risingtest.src.main.main.model.RecommendRvData
import java.text.DecimalFormat

class MainRecommendAdapter (context : Context) : RecyclerView.Adapter<MainRecommendAdapter.MainRecommendViewHolder>(){
    private lateinit var detailClickListener : DetailClickListener
    private var itemList = mutableListOf<RecommendRvData>()
    private val mContext = context
    val myFormatter = DecimalFormat("###,###")


    interface DetailClickListener {
        fun onClick (view : View, position : Int)
    }

    fun detailClickListener (detailClickListener: DetailClickListener) {
        this.detailClickListener = detailClickListener
    }

    inner class MainRecommendViewHolder(val binding : ItemMainRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : RecommendRvData) {
            ApplicationClass.fbStorage.child(item.productImg).downloadUrl.addOnCompleteListener {
                Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(a_resource: Drawable, a_transition: Transition<in Drawable>?) {
                        binding.itemMainRecommendPhoto.background = a_resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
            }
            binding.itemMainRecommendPrice.text = "${myFormatter.format(item.price.toDouble())}원"
            binding.itemMainRecommendLocation.text = item.directAddress
            if(item.securePayment == "SECURE") {
                val spannable = SpannableString("안전 ${item.title}")
                val start : Int = spannable.indexOf("안전")
                val end : Int = start + 2
                spannable.setSpan(ForegroundColorSpan(mContext.resources.getColor(R.color.product_is_safe_color)),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.itemMainRecommendProductName.text = spannable
                binding.itemMainRecommendIsThunderPay.visibility = View.VISIBLE
            } else {
                binding.itemMainRecommendProductName.text = item.title
                binding.itemMainRecommendIsThunderPay.visibility = View.GONE
            }
            if(item.favoriteCount.toInt() == 0) {
                binding.itemMainRecommendHeartLayout.visibility = View.INVISIBLE
            } else {
                binding.itemMainRecommendHeartLayout.visibility = View.VISIBLE
                binding.itemMainRecommendHeartTxt.text = item.favoriteCount
            }
            binding.itemMainRecommendTime.text = item.createdAt

            if(item.myFavorite == "LIKE") {
                binding.itemMainRecommendHeart.setImageResource(R.drawable.icon_heart_filled)
            } else if(item.myFavorite == "UNLIKE") {
                binding.itemMainRecommendHeart.setImageResource(R.drawable.icon_itemlist_heart)
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

        ApplicationClass.fbStorage.child(itemList[position].productImg).downloadUrl.addOnCompleteListener {
            Glide.with(mContext).load(it.result).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(a_resource: Drawable, a_transition: Transition<in Drawable>?) {
                    holder.binding.itemMainRecommendPhoto.background = a_resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        }
        holder.binding.itemMainRecommendLocation.text = itemList[position].directAddress
        if(itemList[position].securePayment == "SECURE") {
            val spannable = SpannableString("안전 ${itemList[position].title}")
            val start : Int = spannable.indexOf("안전")
            val end : Int = start + 2
            spannable.setSpan(ForegroundColorSpan(mContext.resources.getColor(R.color.product_is_safe_color)),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.binding.itemMainRecommendProductName.text = spannable
            holder.binding.itemMainRecommendIsThunderPay.visibility = View.VISIBLE
        } else {
            holder.binding.itemMainRecommendProductName.text = itemList[position].title
            holder.binding.itemMainRecommendIsThunderPay.visibility = View.GONE
        }
        if(itemList[position].favoriteCount.toInt() == 0) {
            holder.binding.itemMainRecommendHeartLayout.visibility = View.INVISIBLE
        } else {
            holder.binding.itemMainRecommendHeartLayout.visibility = View.VISIBLE
            holder.binding.itemMainRecommendHeartTxt.text = itemList[position].favoriteCount
        }
        holder.binding.itemMainRecommendTime.text = itemList[position].createdAt

        holder.itemView.setOnClickListener {
            detailClickListener.onClick(it, itemList[holder.adapterPosition].productIdx)

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setRecommendList(list : MutableList<RecommendRvData>) {
        itemList = list.toMutableList()
        notifyDataSetChanged()
    }
}