package br.com.lucad.kotlinmarmitex.models

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.parcelize.Parcelize


@Parcelize
data class Meal(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double = 0.0,
    val imageUrl: String? = null
) : Parcelable {

}

class Meals() {

    fun getMeals(activity: Fragment) {
    }
}