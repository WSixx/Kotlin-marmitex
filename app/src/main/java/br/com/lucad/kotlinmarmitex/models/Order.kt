package br.com.lucad.kotlinmarmitex.models

import android.os.Build
import android.os.Parcelable
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.extensions.Extensions.errorSnackBar
import br.com.lucad.kotlinmarmitex.extensions.Extensions.sucessSnackBar
import br.com.lucad.kotlinmarmitex.ui.views.PaymentActivity
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import com.google.firebase.firestore.SetOptions
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDateTime

@Parcelize
data class Order(
    var id: String? = null,
    var total: Double = 0.0,
    var meals: Meal? = null,
    var date: @RawValue LocalDateTime? = null
) : Parcelable

class Orders{

    fun registerOrder(activity: PaymentActivity, orderInfo: Order) {
        FirebaseUtils.firebaseFirestore.collection(Constants.ORDERS)
            .document(orderInfo.id!!)
            .set(orderInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.sucessSnackBar("Pedido Cadastrado", activity.findViewById(R.id.activity_payment),  activity.findViewById(
                    R.id.image_view_payment))
                addOrderToUser(orderInfo)

            }
            .addOnFailureListener {
                activity.errorSnackBar("Error ao fazer pedido", activity.findViewById(R.id.activity_payment),  activity.findViewById(
                    R.id.image_view_payment))
            }

    }

    private fun addOrderToUser(orderInfo: Order){
        val orders = HashMap<String, Any>()
        val listOrders = ArrayList<HashMap<String, Any>>()
        val date = orderInfo.date
        val id: String? = orderInfo.id
        val total: Double = orderInfo.total
        val meals: Meal? = orderInfo.meals

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            orders[Constants.ORDER_DATE] = date!!
        }else{
            orders[Constants.ORDER_DATE] = ""
        }
        orders[Constants.ORDER_ID] = id!!
        orders[Constants.ORDER_TOTAL] = total
        orders[Constants.ORDER_MEALS] = meals!!

        listOrders.add(orders)

        SetUser().updateUserOrders(listOrders)
    }

}