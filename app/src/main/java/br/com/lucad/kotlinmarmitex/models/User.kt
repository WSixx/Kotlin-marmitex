package br.com.lucad.kotlinmarmitex.models

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import android.util.Log
import br.com.lucad.kotlinmarmitex.ui.views.EditProfileActivity
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import br.com.lucad.kotlinmarmitex.ui.views.LoginActivity
import br.com.lucad.kotlinmarmitex.ui.views.SettingsActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.SetOptions
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String? = null,
    var username: String? = null,
    var email: String? = null,
    var userPhone: String = "",
    var userDdd: String = "",
    var userCity: String = "",
    var userUF: String = "",
    var userDistrict: String = "",
    var userStreet: String = "",
    var profileIsComplete: Int = 0,
    var userOrders: List<Order>? = null
) : Parcelable

//TODO: POSSO ADD UMA IMAGEM PERFIL DEPOIS ?TALVEZ?

class SetUser {
    fun writeNewUser(user: User) {
        FirebaseUtils.firebaseDatabase.child(Constants.USERS).child(user.id!!).setValue(user)
    }

    fun registerUser(activity: LoginActivity, userInfo: User) {
        FirebaseUtils.firebaseFirestore.collection(Constants.USERS)
            .document(userInfo.id!!)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.hideMyProgressBar()
            }
            .addOnFailureListener {

            }
    }

    //https://firebase.google.com/docs/firestore/manage-data/add-data
    fun updateUserInfo(activity: Activity, userHashMap: HashMap<String, Any>) {
        FirebaseUtils.firebaseFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity) {
                    is EditProfileActivity -> {
                        activity.afterSaveNewInformationSuccessful()
                        activity.finish()
                    }
                }
            }
            .addOnFailureListener { e ->
                when (activity) {
                    is EditProfileActivity -> {
                        activity.hideMyProgressBar()
                        activity.buttonSave.isEnabled = true
                    }
                }
                Log.d("Error Update User: ", e.printStackTrace().toString())
            }
    }

    fun updateUserOrders(ordersHashMap: ArrayList<HashMap<String, Any>>) {
        FirebaseUtils.firebaseFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update("orders", ordersHashMap)
            .addOnSuccessListener {
            }
    }


    fun getCurrentUserId(): String {
        var currentUserId = ""
        val user: FirebaseUser? = FirebaseUtils.firebaseAuth.currentUser
        user?.let {
            currentUserId = FirebaseUtils.firebaseAuth.currentUser?.uid.toString()
        }

        return currentUserId
    }

    //https://firebase.google.com/docs/firestore/query-data/get-data#kotlin+ktx_2
    fun getUserInfo(activity: Activity) {
        println("GET: " + getCurrentUserId())
        FirebaseUtils.firebaseFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)

                saveToMySharedPreferences(activity, user?.username)

                when (activity) {
                    is LoginActivity -> {
                        activity.userIsLogged(user)
                    }

                    is SettingsActivity -> {
                        if (user != null) {
                            activity.dataIsLoaded(user)
                        }
                    }
                }
            }
            .addOnFailureListener {
                when (activity) {
                    is LoginActivity -> {
                        activity.hideMyProgressBar()
                    }
                    is SettingsActivity -> {
                        activity.hideMyProgressBar()
                    }
                }
            }
    }

    private fun saveToMySharedPreferences(activity: Activity, userName: String?) {
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

