package com.jcorp.risingtest.src.main.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemChooseHowToPayBinding
import com.jcorp.risingtest.src.main.main.model.ChoosePaymentData

class ChoosePaymentAdapter (context : Context) : RecyclerView.Adapter<ChoosePaymentAdapter.ChoosePaymentViewHolder>() {

    var paymentsList = mutableListOf<ChoosePaymentData>()
    private val mContext = context
    private lateinit var clickListener : ClickListener

    interface ClickListener {
        fun onClick(view : View, position : Int)
    }
    fun clickListener (clickListener: ChoosePaymentAdapter.ClickListener) {
        this.clickListener = clickListener
    }

    inner class ChoosePaymentViewHolder(val binding : ItemChooseHowToPayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ChoosePaymentData) {
            binding.paymentTitle.text = item.title
            if(item.isChecked) {
                Glide.with(mContext).load(R.drawable.icon_upload_safepay_clicked).into(binding.paymentImg)
                binding.paymentBackground.setBackgroundResource(R.drawable.background_choose_pay_selected)
            }else {
                Glide.with(mContext).load(R.drawable.icon_upload_safepay_unclicked)
                    .into(binding.paymentImg)
                binding.paymentBackground.setBackgroundResource(R.drawable.btn_upload_choose_options)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoosePaymentViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemChooseHowToPayBinding>(layoutInflater, R.layout.item_choose_how_to_pay, parent, false)
        return ChoosePaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChoosePaymentViewHolder, position: Int) {
        holder.bind(paymentsList[position])

        holder.binding.paymentTitle.text = paymentsList[position].title

        if(paymentsList[position].isChecked) {
            Glide.with(mContext).load(R.drawable.icon_upload_safepay_clicked).into(holder.binding.paymentImg)
            holder.binding.paymentBackground.setBackgroundResource(R.drawable.background_choose_pay_selected)
        }else {
            Glide.with(mContext).load(R.drawable.icon_upload_safepay_unclicked)
                .into(holder.binding.paymentImg)
            holder.binding.paymentBackground.setBackgroundResource(R.drawable.btn_upload_choose_options)
        }

        when(position) {
            0 -> holder.binding.paymentAdditional.visibility = View.VISIBLE
            else -> holder.binding.paymentAdditional.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            clickListener.onClick(it, holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return paymentsList.size
    }

    fun setPaymentList(list : MutableList<ChoosePaymentData>) {
        paymentsList = list.toMutableList()
        notifyDataSetChanged()
    }
}