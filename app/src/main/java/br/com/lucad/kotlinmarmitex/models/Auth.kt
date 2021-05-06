package br.com.lucad.kotlinmarmitex.models

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import br.com.lucad.kotlinmarmitex.MainActivity
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast


class Auth() {

    fun signIn(userEmail: String, userPassword: String): Boolean {
        if (isNotEmpty(userEmail, userPassword)) {
            FirebaseUtils.firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { signIn ->
                    val isSuccessful = signIn.isSuccessful
                    return@addOnCompleteListener
                }
        }
        return false
    }

    private fun isNotEmpty(userEmail: String, userPassword: String): Boolean =
        userEmail.isNotEmpty() && userPassword.isNotEmpty()
}