package com.alexd23.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    private val KEY_SOME_RESULT = "MY_KEY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        textView2.text= intent.extras?.getString(Intent.EXTRA_TEXT) ?: "Не повезло не фартануло"
        sButton1.setOnClickListener {
            myResult(1)
        }
        sButton2.setOnClickListener {
            myResult(2)
        }
    }


    private fun myResult(index:Int){
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(KEY_SOME_RESULT, "$index")
        })
        finish()
    }
}