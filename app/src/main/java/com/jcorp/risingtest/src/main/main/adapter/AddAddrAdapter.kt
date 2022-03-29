package com.jcorp.risingtest.src.main.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemAddAddressBinding
import com.jcorp.risingtest.src.main.main.model.PostUserAddrData
import com.jcorp.risingtest.src.main.upload.adapter.UploadRvAdapter

class AddAddrAdapter : RecyclerView.Adapter<AddAddrAdapter.AddAddrViewHolder>() {
    private var addList = mutableListOf<PostUserAddrData>()
    lateinit var deleteListener : DeleteListener

    interface DeleteListener {
        fun deleteClick (view : View, position : Int)
    }

    fun deleteListener (deleteListener: DeleteListener) {
        this.deleteListener = deleteListener
    }


    inner class AddAddrViewHolder(val binding : ItemAddAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : PostUserAddrData) {
            binding.itemAddAddressTitle.text = item.name
            binding.itemAddAddressAddr.text = item.address
            binding.itemAddAddressDetailAddr.text = item.detailAddress
            binding.itemAddAddressPhone.text = item.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAddrViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAddAddressBinding>(inflater, R.layout.item_add_address, parent, false)
        return AddAddrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddAddrViewHolder, position: Int) {
        holder.bind(addList[position])

        holder.binding.itemAddAddressTitle.text = addList[position].name
        holder.binding.itemAddAddressAddr.text = addList[position].address
        holder.binding.itemAddAddressDetailAddr.text = addList[position].detailAddress
        holder.binding.itemAddAddressPhone.text = addList[position].phone

        holder.binding.itemAddAddressDelete.setOnClickListener {
            deleteListener.deleteClick(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return addList.size
    }

    fun setAddrList(list : MutableList<PostUserAddrData>) {
        addList = list.toMutableList()
        notifyDataSetChanged()
    }
}