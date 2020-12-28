package com.alexd23.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme2)
        setContentView(R.layout.activity_login)
        login.setOnClickListener{
            if(et_name.text.toString().isNotEmpty() && et_surname.text.toString().isNotEmpty()) {
                val myIntent = Intent(this, MainActivity::class.java)
                myIntent.putExtra("name", et_name.text.toString())
                myIntent.putExtra("surname", et_surname.text.toString())
                myIntent.putExtra("currentCity", et_currentCity.text.toString())
                myIntent.putExtra("workplace", et_workplace.text.toString())
                myIntent.putExtra("hometown", et_hometown.text.toString())
                startActivity(myIntent)
            }
            else{
                Toast.makeText(this,"Fill in all the fields",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}