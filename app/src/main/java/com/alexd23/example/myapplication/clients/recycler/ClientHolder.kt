package com.alexd23.example.myapplication.clients.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexd23.example.myapplication.entities.Client
import com.alexd23.example.myapplication.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.client_item.*

class ClientHolder(
    override val containerView: View,
    val itemClick:  (Int) -> Unit
) : LayoutContainer,RecyclerView.ViewHolder(containerView) {

    fun bind(client: Client){
        title_ci.text = client.name
        description_ci.text = client.description
        deleteUser.setOnClickListener {
            itemClick.invoke(adapterPosition)
        }
    }

    fun updateFromBundle(bundle : Bundle?){
        if(bundle?.containsKey("ARG_NAME") == true){
            bundle.getString("ARG_NAME").also {
                title_ci.text = it
            }
        }
        if(bundle?.containsKey("ARG_DESCRIPTION") == true){
            bundle.getString("ARG_DESCRIPTION").also {
                description_ci.text = it
            }
        }
    }

    companion object{
        fun create(parent: ViewGroup,itemClick:  (Int) -> Unit): ClientHolder =
            ClientHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.client_item,
                    parent,
                    false
                ),
                itemClick
            )
    }

}