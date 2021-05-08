package br.com.lucad.kotlinmarmitex.models

import android.util.Log
import android.widget.Toast
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils

data class Meal(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double = 0.0,
    val imageUrl: String? = null
) {

}

class Meals() {

    fun addToCart(mealId: String?, mealTitle: String) {
        val meals = MealCart(mealId, mealTitle)
        FirebaseUtils.firebaseDatabase.child("cart").child(mealId!!).setValue(meals)
    }

    fun checkMeals(mealId: String?, mealTitle: String) {
        var url = "https://kotlinmarmitex-default-rtdb.firebaseio.com/meals"
        FirebaseUtils.firebaseDatabase.child("meals").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}