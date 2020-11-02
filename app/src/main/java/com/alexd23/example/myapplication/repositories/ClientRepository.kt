package com.alexd23.example.myapplication.repositories

import com.alexd23.example.myapplication.entities.Client

interface ClientRepository {
    fun addClient(client: Client)
    fun addClient(client: Client, index: Int)
    fun deleteClient(clientId: Int)
    fun getClients(): Collection<Client>
    fun getSize():Int
}