package com.alexd23.example.myapplication.repositories

import com.alexd23.example.myapplication.R
import com.alexd23.example.myapplication.entities.Card

object Cards {
    private var list = listOf(
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString()),
        Card("Заголовок"+(Math.random()*1000).toString(), Images.getImages(), "Описание номер какое то "+(Math.random()*1000).toString())
    )


    fun getCards(): List<Card>{
        return list.filter{ (Math.random()*1000).toInt()%2 == 0 }
    }
}