package com.alexd23.example.myapplication

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()
        var cityEdit = cityEdit
        var workEdit = workEdit
        var hometownEdit = hometownEdit
        var cityText = cityText
        var workText = workText
        var hometownText = hometownText
        var map = mapOf<EditText, TextView>(Pair(cityEdit,cityText), Pair(workEdit,workText),Pair(hometownEdit,hometownText))
        editButton.setOnClickListener{
            if(editButton.text == "edit"){
                editButton.text = "save"
                for(elem in container.children){
                    if(elem.tag =="edit"){
                        elem.visibility = View.VISIBLE
                    }
                    if(elem.tag =="text"){
                        elem.visibility = View.INVISIBLE
                    }
                }
            }
            else{
                editButton.text = "edit"
                for(elem in map){
                    elem.key.visibility = View.INVISIBLE
                    elem.value.text = elem.key.text.toString()
                    elem.value.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initialization() {
        article.text = "${intent.extras?.getString("name") ?: ""} ${intent.extras?.getString("surname") ?: ""}"
        cityEdit.setText(intent.extras?.getString("currentCity") ?: "")
        cityText.text = intent.extras?.getString("currentCity") ?: ""
        workEdit.setText(intent.extras?.getString("workplace") ?: "")
        workText.text = intent.extras?.getString("workplace") ?: ""
        hometownEdit.setText(intent.extras?.getString("hometown") ?: "")
        hometownText.text = intent.extras?.getString("hometown") ?: ""
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile-> Toast.makeText(this,"Ну нет", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(this,"Ну нет", Toast.LENGTH_SHORT)
        }
        return super.onOptionsItemSelected(item)
    }




}
