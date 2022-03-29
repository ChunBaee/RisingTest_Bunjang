package com.jcorp.risingtest.src.main.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemDialogAddressBinding
import com.jcorp.risingtest.src.main.main.model.PostUserAddrData

class ChooseAddrAdapter : RecyclerView.Adapter<ChooseAddrAdapter.ChooseAddrViewHolder>() {

    private var addrList = mutableListOf<PostUserAddrData>()

    inner class ChooseAddrViewHolder (val binding : ItemDialogAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : PostUserAddrData) {
            binding.itemAddressMain.text = item.address
            binding.itemAddressSub.text = item.detailAddress
            binding.itemAddressPhone.text = item.phone.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseAddrViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDialogAddressBinding>(inflater, R.layout.item_dialog_address, parent, false)
        return ChooseAddrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseAddrViewHolder, position: Int) {
        holder.bind(addrList[position])
    }

    override fun getItemCount(): Int {
        return addrList.size
    }

    fun setAddrList(list : MutableList<PostUserAddrData>) {
        addrList = list.toMutableList()
        notifyDataSetChanged()
    }
}