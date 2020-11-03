package com.alexd23.example.myapplication.repositories

import com.alexd23.example.myapplication.R

object Images {
    private var list = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image8
    )
    fun getImages(): List<Int>{
        return list.filter { i -> (Math.random()*1000).toInt()%2 == 0 }
    }
}