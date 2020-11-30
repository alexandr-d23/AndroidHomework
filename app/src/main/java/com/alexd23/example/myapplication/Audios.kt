package com.alexd23.example.myapplication

import android.util.Log
import java.util.*

object Audios {

    var list: LinkedList<Audio> = LinkedList( listOf(
        Audio(1,"Young hefner", "Morgenshern", R.raw.yung_hefner, R.raw.icon_young_hefner),
            Audio(2,"Cadillac", "Моргенштерн элджей", R.raw.cadillac, R.raw.icon_cadillac),
            Audio(3,"Поболело и прошло", "Hensy", R.raw.hensy, R.raw.icon_hensy),
            Audio(4,"Ауф", "Нурминский", R.raw.auf, R.raw.icon_auf),
            Audio(5,"Витаминка", "Тима Белорусских", R.raw.tima_bel_vitamine, R.raw.icon_vitamine),
            Audio(6,"Юность", "Dabro", R.raw.dabro_youth, R.raw.icon_dabro)
    ))

    fun getAllAudio():List<Audio> = list

    fun nextAudio(id: Int) : Audio?{
        Log.d("check","${"Полученный id"+id}")
        for(i in 0 until list.size-1){
            if(list[i].id == id){
                Log.d("check","возвращённый id ${list[i+1].id}")
                return list[i+1]
            }
        }
        return null
    }

    fun previousAudio(id:Int): Audio?{
        for(i in 1 until list.size){
            if(list[i].id == id){
                return list[i-1]
            }
        }
        return null
    }





}