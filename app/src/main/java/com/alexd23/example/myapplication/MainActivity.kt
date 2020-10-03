package com.alexd23.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val KEY_SOME_RESULT = "MY_KEY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_TEXT, "Очень экстренный текст даже не представляешь")
            if(intent.resolveActivity(packageManager)!=null) {
                startActivityForResult(intent, 14)
            }
            else{
                Toast.makeText(this,intent.categories.size, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            Toast.makeText(this, "Пользователь нажал кнопку ${data?.extras?.getString(KEY_SOME_RESULT)}", Toast.LENGTH_SHORT).show()
        }
    }
}