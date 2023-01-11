package com.example.project_contact.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_contact.DataBase.UserData
import com.example.swipe_layout.databinding.ItemSavedBinding


class UserAdapter(
    val data: ArrayList<UserData>,
    val onItemDelte: (userData: UserData, position: Int) -> Unit,
    val onItemEdit: (userData: UserData, position: Int) -> Unit,
    val onItemCall: (userData: UserData, position: Int) -> Unit,
    val onItemSms: (userData: UserData, position: Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.VH>() {

    inner class VH(val itemUserBinding: ItemSavedBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(data: UserData, position: Int) {
            itemUserBinding.apply {
                tvUserName.text = data.user_name
                tvUserPhone.text = data.user_Phone_number
                firstApl.text = data.firstAt

                btnDelet.setOnClickListener {
                    onItemDelte.invoke(data, position)
                }
                btnEdit.setOnClickListener {
                    onItemEdit.invoke(data, position)
                }
                btnPhone.setOnClickListener {
                    onItemCall.invoke(data, position)
                }
                btnSms.setOnClickListener {
                    onItemSms.invoke(data, position)
                }

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(data[position], position)

    }

    override fun getItemCount(): Int {
        return data.size

    }
}