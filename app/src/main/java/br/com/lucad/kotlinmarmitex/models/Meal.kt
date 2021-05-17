package br.com.lucad.kotlinmarmitex.models

import android.os.Parcelable
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import kotlinx.parcelize.Parcelize


@Parcelize
data class Meal(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double = 0.0,
    val votos: Int = 0,
    val quantity: Int = 0,
    val images: List<String>? = null
) : Parcelable

class Meals {

    fun updateMealQuantity(mealId: String, mealHashMap: HashMap<String, Any>) {
        FirebaseUtils.firebaseFirestore.collection(Constants.MEALS)
            .document(mealId)
            .update(mealHashMap)
            .addOnSuccessListener {
                println("Sucess")

            }
            .addOnFailureListener {
                println("Failure")
            }
    }
}