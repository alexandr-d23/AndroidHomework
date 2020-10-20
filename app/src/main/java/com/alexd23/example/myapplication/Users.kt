package com.alexd23.example.myapplication

object Users {

        private val users = HashMap<Int, User>()

        fun getUsers(): Collection<User> {

            return users.values
        }

        fun freshUsers(): Collection<User> {
            updateData()
            return users.values
        }

        fun getUser(id: Int): User? = users[id]

        private fun updateData() {
            for (i in 1..100) {
                users[i] = User(i, "Azat", R.drawable.t1, "Groupmate $i")
            }
        }

}