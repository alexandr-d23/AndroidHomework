package com.example.hw4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hw4.R

class FirstFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first,container,false)
    }

    companion object {
        fun newInstance(): FirstFragment {
            val args = Bundle()
            val fragment = FirstFragment()
            fragment.arguments = args
            return fragment
        }
    }
}