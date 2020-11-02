package com.alexd23.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.alexd23.example.myapplication.clients.recycler.AddUserDialog
import com.alexd23.example.myapplication.clients.recycler.ClientsAdapter
import com.alexd23.example.myapplication.clients.recycler.ItemTouchHelperAdapter
import com.alexd23.example.myapplication.clients.recycler.MyItemTouchHelper
import com.alexd23.example.myapplication.entities.Client
import com.alexd23.example.myapplication.repositories.ClientRepository
import com.alexd23.example.myapplication.repositories.Clients
import kotlinx.android.synthetic.main.bottom_fragment2.*

class BottomFragment2 : Fragment() {
    var clientRep : ClientRepository =
        Clients
    var adapter : ClientsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_fragment2, container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter =
            ClientsAdapter(
                clientRep.getClients().toList()
            ) {
                clientRep.deleteClient(it)
                updateData()
            }
        recycler_fr2.adapter = adapter
        var callback =
            MyItemTouchHelper(
                adapter as ItemTouchHelperAdapter
            )
        var touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler_fr2)
        recycler_fr2.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        fab_fr2.setOnClickListener {
            var dialog =
                AddUserDialog()
            var myListener : (String, String, Int) -> Unit = { name, description, position ->
                var client = Client(
                    name,
                    description
                )
                if(position>clientRep.getSize() || position < 0){
                    clientRep.addClient(client)
                }
                else{
                    clientRep.addClient(client, position)
                }
                updateData()

            }
            dialog.listener = myListener
//            dialog.setTargetFragment(this, 1)
            dialog.show(requireFragmentManager(), "dialog")

        }

    }

    private fun updateData(){
        adapter?.updateDataSource(clientRep.getClients().toList())
    }



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            when (requestCode) {
//                1 -> {
//                    val name = data?.extras?.getString("name")
//                    val description = data?.extras?.getString("description")
//                    if (name != null && description != null) {
//                        clientRep.addClient(
//                            Client(
//                                name,
//                                description
//                            )
//                        )
//                        var list2 = clientRep.getClients().toList()
//                        adapter?.updateDataSource(list2)
//                    }
//                }
//            }
//        }
//    }

    companion object{
        fun newInstance(): BottomFragment2 {
            val args = Bundle()
            val fragment = BottomFragment2()
            fragment.arguments = args
            return fragment
        }
    }
}