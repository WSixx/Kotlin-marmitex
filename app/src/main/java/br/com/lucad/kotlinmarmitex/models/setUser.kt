package br.com.lucad.kotlinmarmitex.models

import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils

class setUser {

    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)

        FirebaseUtils.firebaseDatabase.child("users").child(userId).setValue(user)
    }
}