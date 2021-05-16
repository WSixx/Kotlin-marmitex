package br.com.lucad.kotlinmarmitex.models

import android.app.Activity
import android.os.Parcelable
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import com.google.firebase.firestore.SetOptions
import kotlinx.parcelize.Parcelize
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Parcelize
data class Order(
    var id: String? = null,
    var userId: String? = null,
    var total: Double = 0.0,
    var meals: ArrayList<Meal>? = null,
    var date: String? = null,
) : Parcelable

class Orders{

    fun registerOrder(orderInfo: Order) {
        FirebaseUtils.firebaseFirestore.collection(Constants.ORDERS)
            .document(orderInfo.id!!)
            .set(orderInfo, SetOptions.merge())
            .addOnSuccessListener {
                addOrderToUser(orderInfo)

            }
            .addOnFailureListener {

            }

    }

    private fun addOrderToUser(orderInfo: Order){
        val orders = HashMap<String, Any>()
        val listOrders = ArrayList<HashMap<String, Any>>()
        val date = orderInfo.date
        val id: String? = orderInfo.id
        val total: Double = orderInfo.total
        val meals: ArrayList<Meal> = orderInfo.meals!!


        orders[Constants.ORDER_ID] = id!!
        orders[Constants.ORDER_TOTAL] = total
        orders[Constants.ORDER_MEALS] = meals!!

        listOrders.add(orders)

        SetUser().updateUserOrders(listOrders)
    }

}