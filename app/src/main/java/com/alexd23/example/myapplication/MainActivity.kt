package com.alexd23.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var ivan = People(24,"Иван", "Иванов", "Казань")
        ivan.info()
        Log.d("TAG", ivan.toBreath())
        var azat = Baby(3,"Азат", "Гилязов", "Туймазы")
        azat.toCry()
        azat.info()
    }
}