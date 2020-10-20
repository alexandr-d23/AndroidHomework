package com.alexd23.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fr_recycler.*
import java.lang.ClassCastException

class RecyclerFragment : Fragment() {
    lateinit var rListener : OnFragmentInteractionListener
    interface OnFragmentInteractionListener{
        fun onFragmentInteraction(id: Int)
    }

    lateinit var recycler : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_recycler,container,false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            rListener = context as OnFragmentInteractionListener
        }
        catch (e: ClassCastException){
            throw e
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("kek","log")
        recycler = my_recycler
        recycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        val list = Users.freshUsers().toMutableList()
        val userAdapter = UserAdapter(list){
            Log.d("kek","log")
            rListener.onFragmentInteraction(it)

        }
        recycler.adapter = userAdapter
    }

    fun remove(id : Int){

    }

    fun newInstance(): RecyclerFragment {
        val args = Bundle()
        val fragment = RecyclerFragment()
        fragment.arguments = args
        return fragment
    }
}