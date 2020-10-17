package com.example.hw4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hw4.R

class FifthFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fifth,container,false)
    }

    companion object {
        fun newInstance(): FifthFragment {
            val args = Bundle()
            val fragment = FifthFragment()
            fragment.arguments = args
            return fragment
        }
    }
}