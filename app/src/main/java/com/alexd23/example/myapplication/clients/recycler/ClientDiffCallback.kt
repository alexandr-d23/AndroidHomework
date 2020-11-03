package com.alexd23.example.myapplication.clients.recycler

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.alexd23.example.myapplication.entities.Client

class ClientDiffCallback(
    private val oldList:List<Client>,
    private val newList:List<Client>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val bundle = Bundle().apply {
            if(oldList[oldItemPosition].name != newList[newItemPosition].name){
                putString("ARG_NAME",newList[newItemPosition].name)
            }
            if(oldList[oldItemPosition].description != newList[newItemPosition].description){
                putString("ARG_DESCRIPTION",newList[newItemPosition].description)
            }
        }
        return if(bundle.isEmpty) null else bundle
    }

}