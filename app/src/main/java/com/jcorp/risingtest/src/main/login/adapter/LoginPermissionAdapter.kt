package com.jcorp.risingtest.src.main.login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.risingtest.R
import com.jcorp.risingtest.databinding.ItemDialogAcceptPermissionBinding
import com.jcorp.risingtest.src.main.login.model.LoginPermissionData

class LoginPermissionAdapter : RecyclerView.Adapter<LoginPermissionAdapter.LoginPermissionViewHolder>() {
    var permissionList = mutableListOf<LoginPermissionData>()
    private lateinit var clickListener : ClickListener

    interface ClickListener {
        fun onClick(view : View, position : Int)
    }
    fun clickListener (clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    inner class LoginPermissionViewHolder (val binding : ItemDialogAcceptPermissionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : LoginPermissionData) {
            binding.itemAcceptPermissionText.text = item.text
            if(item.isClicked) {
                binding.itemAcceptPermissionCheck.setImageResource(R.drawable.icon_upload_safepay_clicked)
            } else {
                binding.itemAcceptPermissionCheck.setImageResource(R.drawable.icon_upload_safepay_unclicked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginPermissionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDialogAcceptPermissionBinding>(layoutInflater, R.layout.item_dialog_accept_permission, parent, false)
        return LoginPermissionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoginPermissionViewHolder, position: Int) {
        holder.bind(permissionList[position])

        holder.binding.itemAcceptPermissionText.text = permissionList[position].text
        if(permissionList[position].isClicked) {
            holder.binding.itemAcceptPermissionCheck.setImageResource(R.drawable.icon_upload_safepay_clicked)
        } else {
            holder.binding.itemAcceptPermissionCheck.setImageResource(R.drawable.icon_upload_safepay_unclicked)
        }

        holder.itemView.setOnClickListener {
            clickListener.onClick(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return permissionList.size
    }

    fun setList (list : MutableList<LoginPermissionData>) {
        permissionList = list.toMutableList()
        notifyDataSetChanged()
    }
}