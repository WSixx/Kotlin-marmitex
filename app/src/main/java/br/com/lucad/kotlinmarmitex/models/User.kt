package br.com.lucad.kotlinmarmitex.models

import android.util.Log
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import br.com.lucad.kotlinmarmitex.views.LoginActivity
import com.google.firebase.firestore.SetOptions

data class User(
    var id: String? = null,
    var username: String? = null,
    var email: String? = null
) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

class SetUser {
    fun writeNewUser(user: User) {
        FirebaseUtils.firebaseDatabase.child("users").child(user.id!!).setValue(user)
    }

    fun registerUser(activity: LoginActivity, userInfo: User) {
        FirebaseUtils.firebaseFirestore.collection("users")
            .document(userInfo.id!!)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                //activity.toast("Cadastro feito")
            }
            .addOnFailureListener {
                Log.d("Register", "Error ao cadastrar Usuario")
                activity.toast("Error ao cadastrar Usuario - Register")
            }
    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseUtils.firebaseUser

        //
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        return currentUserId
    }
}