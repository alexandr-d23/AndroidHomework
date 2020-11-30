package com.alexd23.example.myapplication.playlist.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexd23.example.myapplication.R
import com.alexd23.example.myapplication.Audio
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.music_item.view.*

class AudioHolder(
        override val containerView: View,
        private val callBack: (Audio) -> Unit
) : RecyclerView.ViewHolder(containerView),LayoutContainer {

    companion object{
        fun createHolder(parent: ViewGroup,callBack: (Audio) -> Unit):AudioHolder =
                AudioHolder(LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,false),callBack)
    }

    fun bind(audio: Audio){
        containerView.item_music_name.text = audio.name
        containerView.item_music_author.text = audio.author
        containerView.item_music_icon.setImageResource(audio.icon)
        containerView.item_music.setOnClickListener {
            callBack.invoke(audio)
            Log.d("Check", "${audio.name}")
        }
    }




}