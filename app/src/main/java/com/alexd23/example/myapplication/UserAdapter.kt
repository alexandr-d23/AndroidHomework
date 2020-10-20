package com.alexd23.example.myapplication


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserAdapter (
    private val list: List<User>,
    private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<UserHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder = UserHolder.create(parent, itemClick)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) = holder.bind(list[position])


}