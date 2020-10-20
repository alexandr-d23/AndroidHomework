package com.alexd23.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_user.*

class MainActivity : AppCompatActivity(), RecyclerFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RecyclerFragment().newInstance()).commit()

    }

    override fun onFragmentInteraction(id: Int) {
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, UserFragment.newInstance(id)).commit()
    }
}