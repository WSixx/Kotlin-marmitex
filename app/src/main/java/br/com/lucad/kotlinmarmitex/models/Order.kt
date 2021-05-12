package br.com.lucad.kotlinmarmitex.models

import android.os.Parcelable
import com.google.type.DateTime
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Order(
    val id: String? = null,
    val total: Double = 0.0,
    val meals: List<Meal>? = null,
    val date: @RawValue DateTime? = null
) : Parcelable {

}

class Orders(){

}