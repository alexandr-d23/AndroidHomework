package com.alexd23.example.myapplication.playlist.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexd23.example.myapplication.Audio

class AudioAdapter(
    private val list: List<Audio>,
    private val callBack: (Audio) -> Unit
) : RecyclerView.Adapter<AudioHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder = AudioHolder.createHolder(parent,callBack)

    override fun onBindViewHolder(holder: AudioHolder, position: Int) = holder.bind(list[position])


    override fun getItemCount(): Int = list.size

}