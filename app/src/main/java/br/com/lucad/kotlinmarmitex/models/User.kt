package br.com.lucad.kotlinmarmitex.models

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import br.com.lucad.kotlinmarmitex.views.LoginActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.SetOptions

data class User(
    var id: String? = null,
    var username: String? = null,
    var email: String? = null,
    var phoneNumber: String = "",
    var profileIsComplete: Int = 0
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
                activity.hideProgressLogin()
            }
            .addOnFailureListener {
                activity.toast("Error ao cadastrar Usuario - Register")
            }
    }

    private fun getCurrentUserId(): String {
        val currentUser = FirebaseUtils.firebaseUser

        var currentUserId = ""
        val user: FirebaseUser? = FirebaseUtils.firebaseAuth.currentUser
        user?.let {
            currentUserId = FirebaseUtils.firebaseAuth.currentUser?.uid.toString()
        }

        return currentUserId
    }

    //https://firebase.google.com/docs/firestore/query-data/get-data#kotlin+ktx_2
    fun getUserInfo(activity: Activity){
        println("GET: " + getCurrentUserId())
        FirebaseUtils.firebaseFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)

                saveToMySharedPreferences(activity, user?.username)

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

    private fun saveToMySharedPreferences(activity: Activity, userName: String?){
        val sharedPreferences = activity.getSharedPreferences(
            Constants.MY_SHARE_PREF,
            Context.MODE_PRIVATE
        )
        //key: Constants.USERNAME_LOGGED
        //value: username
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(
            Constants.USERNAME_LOGGED,
            "$userName"
        )
        editor.apply()
    }
}