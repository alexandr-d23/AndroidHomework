package com.alexd23.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.container_main, BottomFragment1.newInstance()).commit()

        bnv_main.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1_bnv ->{
                    supportFragmentManager.beginTransaction().replace(R.id.container_main, BottomFragment1.newInstance()).commit()
                    true
                }
                R.id.item2_bnv ->{
                    supportFragmentManager.beginTransaction().replace(R.id.container_main, BottomFragment2.newInstance()).commit()
                    true
                }
                R.id.item3_bnv -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container_main, BottomFragment3.newInstance()).commit()
                    true
                }
                else -> false
            }
        }
        bnv_main.setOnNavigationItemReselectedListener {  }
    }
}