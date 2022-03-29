package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemShippingOptionsBinding
import com.jcorp.risingtest.src.main.login.adapter.LoginUserTelecomAdapter
import com.jcorp.risingtest.src.main.main.model.ShippingOptionsData

class ChooseShipOptionsAdapter (context : Context) : RecyclerView.Adapter<ChooseShipOptionsAdapter.ChooseShipOptionsViewHolder>() {
    var optionsList = mutableListOf<ShippingOptionsData>()
    val mContext = context
    private lateinit var clickListener : ClickListener

    interface ClickListener {
        fun onClick(view : View, position : Int)
    }
    fun clickListener (clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    inner class ChooseShipOptionsViewHolder(val binding : ItemShippingOptionsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ShippingOptionsData) {
            binding.itemShippingOptionsTitle.text = item.title
            if(item.isClicked) {
                binding.itemShippingOptionsTitle.setTextColor(mContext.resources.getColor(R.color.upload_isnew_txt_selected_color))
            } else {
                binding.itemShippingOptionsTitle.setTextColor(mContext.resources.getColor(R.color.product_detail_tooltip_txt_color))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseShipOptionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemShippingOptionsBinding>(layoutInflater, R.layout.item_shipping_options, parent, false)
        return ChooseShipOptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseShipOptionsViewHolder, position: Int) {
        holder.bind(optionsList[position])

        holder.binding.itemShippingOptionsTitle.text = optionsList[position].title
        if(optionsList[position].isClicked) {
            holder.binding.itemShippingOptionsTitle.setTextColor(mContext.resources.getColor(R.color.upload_isnew_txt_selected_color))
        } else {
            holder.binding.itemShippingOptionsTitle.setTextColor(mContext.resources.getColor(R.color.product_detail_tooltip_txt_color))
        }

        holder.itemView.setOnClickListener {
            clickListener.onClick(it, holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    fun setOptionList(list : MutableList<ShippingOptionsData>) {
        optionsList = list.toMutableList()
        notifyDataSetChanged()
    }
}