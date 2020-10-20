package com.alexd23.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_user.*

class UserFragment :  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_user,container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = arguments?.getInt("id")
        id?.let {
            var user = Users.getUser(it)
            user?.let { u->
                ft_tv_description.text = u.description
                ft_tv_name.text = u.name
                ft_item_iv.setImageResource(u.image)
            }
        }
    }

    companion object{
        fun newInstance(id: Int): UserFragment {
            val args = Bundle()
            args.putInt("id", id)
            val fragment = UserFragment()
            fragment.arguments = args
            return fragment
        }
    }
}