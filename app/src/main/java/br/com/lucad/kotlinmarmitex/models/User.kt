package br.com.lucad.kotlinmarmitex.models

import android.app.Activity
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.utils.Constants
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
        FirebaseUtils.firebaseDatabase.child(Constants.USERS).child(user.id!!).setValue(user)
    }

    fun registerUser(activity: LoginActivity, userInfo: User) {
        FirebaseUtils.firebaseFirestore.collection(Constants.USERS)
            .document(userInfo.id!!)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                //activity.toast("Cadastro feito")
                activity.hideProgressLogin()
            }
            .addOnFailureListener {
                activity.toast("Error ao cadastrar Usuario - Register")
            }
    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseUtils.firebaseUser

        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

    fun getUserInfo(activity: Activity){
        println(getCurrentUserId())
        FirebaseUtils.firebaseFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)

                when(activity){
                    is LoginActivity -> {
                        activity.userIsLogged(user)
                    }
                }
            }
            .addOnFailureListener{
                when(activity){
                    is LoginActivity -> {
                        activity.hideProgressLogin()
                    }
                }
            }
    }
}