package com.alexd23.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexd23.example.myapplication.cardview.recycler.CardAdapter
import com.alexd23.example.myapplication.repositories.Cards
import kotlinx.android.synthetic.main.bottom_fragment3.*

class BottomFragment3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_fragment3, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_fr3.adapter = CardAdapter(Cards.getCards())
        recycler_fr3.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
      //  viewPager.adapter = ViewPagerAdapter(Images.getImages())
    }

    companion object{
        fun newInstance(): BottomFragment3 {
            val args = Bundle()
            val fragment = BottomFragment3()
            fragment.arguments = args
            return fragment
        }
    }
}