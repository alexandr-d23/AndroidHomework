package com.alexd23.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.android.synthetic.main.item_user.view.*

class UserHolder(
    override val containerView: View,
    private val itemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {


    fun bind(user : User){
        with(user){
            tv_description.text = description
            tv_name.text = name
            item_iv.setImageResource(user.image)
        }
        itemView.setOnClickListener {
            itemClick(user.id)
        }
    }

    companion object{
        fun create(parent: ViewGroup, itemClick: (Int) -> Unit): UserHolder =  UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,false), itemClick)
    }

}