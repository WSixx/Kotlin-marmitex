package br.com.lucad.kotlinmarmitex.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Auth() {
    private var auth: FirebaseAuth =  Firebase.auth

    fun checkIsLogin(): Boolean{
        // Initialize Firebase Auth

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        return currentUser != null
    }

}