package com.alexd23.example.myapplication.clients.recycler

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import com.alexd23.example.myapplication.R
import java.lang.NumberFormatException

class AddUserDialog : DialogFragment() {
    lateinit var listener : (name: String, description : String, position : Int ) -> Unit
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_user_dialog,null)
        val name = view.findViewById<AppCompatEditText>(R.id.dialog_et_name)
        val description = view.findViewById<AppCompatEditText>(R.id.dialog_et_description)
        val number = view.findViewById<AppCompatEditText>(R.id.dialog_et_position)
        builder.setView(view)
            .setPositiveButton("Добавить"){a,b->
                val name : String = name.text.toString()
                val description : String =  description.text.toString()
                val number : Int =
                    try{number.text.toString().toInt()-1}
                    catch (e: NumberFormatException){
                        -1
                    }
                listener.invoke(name,description,number)
            }
            .setNegativeButton("Отмена") { _, _ ->

            }



        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}