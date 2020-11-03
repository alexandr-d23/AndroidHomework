package com.alexd23.example.myapplication.repositories

import com.alexd23.example.myapplication.entities.Client
import java.util.*

object Clients :
    ClientRepository {
    var clients: LinkedList<Client> = LinkedList()
    init {
        var i = 1
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
        clients.add(
            Client(
                "${i++} " + (Math.random() * 1000).toString(),
                (Math.random() * 1000).toString()
            )
        )
    }
    override fun addClient(client : Client) {
        clients.addLast(client)
    }

    override fun addClient(client: Client, index: Int) {
        clients.add(index,client)
    }

    override fun deleteClient(clientId: Int) {
        clients.removeAt(clientId)
    }

    override fun getClients(): Collection<Client> =
        clients

    override fun getSize(): Int = clients.size

}