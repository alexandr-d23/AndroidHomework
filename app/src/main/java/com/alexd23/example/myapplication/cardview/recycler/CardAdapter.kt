package com.alexd23.example.myapplication.cardview.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.alexd23.example.myapplication.R
import com.alexd23.example.myapplication.entities.Card
import com.alexd23.example.myapplication.photos.pager.MyPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.card_item.view.*

class CardAdapter(
    var cards:List<Card>
) : RecyclerView.Adapter<CardAdapter.CardHolder>() {

    inner class CardHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(card : Card){
            itemView.viewPager.adapter = MyPagerAdapter(card.images)
            itemView.card_title.text = card.title
            var tabLayout : TabLayout = itemView.tab_layout
            tabLayout.setupWithViewPager(itemView.viewPager, true)
            itemView.card_description.text = card.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder =
        CardHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(cards[position])
    }


}