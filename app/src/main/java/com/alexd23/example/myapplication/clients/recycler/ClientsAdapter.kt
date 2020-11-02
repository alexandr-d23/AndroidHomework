package com.alexd23.example.myapplication.clients.recycler

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexd23.example.myapplication.entities.Client

class ClientsAdapter(
    private var list: List<Client>,
    private val itemClick:  (Int) -> Unit
) : RecyclerView.Adapter<ClientHolder>(),
    ItemTouchHelperAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder = ClientHolder.create(parent,itemClick)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ClientHolder, position: Int) = holder.bind(list[position])

    override fun onBindViewHolder(holder: ClientHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        }
        else{
            (payloads[0] as Bundle)?.also{
                holder.updateFromBundle(it)
            }
        }
    }

    fun updateDataSource(newList: List<Client>){
        val callback =
            ClientDiffCallback(
                list,
                newList
            )
        val result = DiffUtil.calculateDiff(callback, true)
        result.dispatchUpdatesTo(this)
        list = mutableListOf<Client>().apply{addAll(newList)}
    }

    override fun onItemSwiped(position: Int) {
        itemClick.invoke(position)
    }

}
