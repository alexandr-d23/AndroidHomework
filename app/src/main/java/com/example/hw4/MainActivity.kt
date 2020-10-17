package com.example.hw4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.hw4.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
        var selectedImage : ImageView? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageButton1.setOnClickListener {
            if(selectedImage!=imageButton1){
            setSelect(imageButton1)
            setFragment(FirstFragment.newInstance())
            }
        }
        imageButton2.setOnClickListener {
            if(selectedImage!=imageButton2) {
                setSelect(imageButton2)
                setFragment(SecondFragment.newInstance())
            }
        }
        imageButton3.setOnClickListener {
            if(selectedImage!=imageButton3) {
                setSelect(imageButton3)
                setFragment(ThirdFragment.newInstance())
            }
        }
        imageButton4.setOnClickListener {
            if(selectedImage!=imageButton4) {
                setSelect(imageButton4)
                setFragment(FourthFragment.newInstance())
            }
        }
        imageButton5.setOnClickListener {
            if(selectedImage!=imageButton5) {
                setSelect(imageButton5)
                setFragment(FifthFragment.newInstance())
            }
        }
    }

    private fun setFragment(fr: Fragment){
        supportFragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_left,R.animator.slide_in_rigth).replace(R.id.fragmentContainer,fr).commit()
    }

    private fun setSelect(iv : ImageView){
        iv.isSelected=true
        if(selectedImage != iv){
            selectedImage?.isSelected=false
            selectedImage = iv
        }
    }

}