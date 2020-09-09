package com.alexd23.example.myapplication

import android.util.Log

open class People(var age: Int, var firstName: String, var  lastName: String, var position : String) :Living {

    override fun toBreath() :String {
        return "Я человек и я дышу"
    }

    override fun toGo(place: String) {
        position = place
        Log.d("TAG","Теперь я нахожусь в $position")
    }

    fun info(){
        Log.d("TAG","Я $lastName $firstName, мне $age лет и я нахожусь в $position ")
    }


}