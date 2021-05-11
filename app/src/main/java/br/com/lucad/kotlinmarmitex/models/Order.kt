package br.com.lucad.kotlinmarmitex.models

import com.google.type.DateTime

data class Order(
    val id: String? = null,
    val total: Double = 0.0,
    val products: List<MealCart>? = null,
    val date: DateTime? = null
) {

}

class Orders(){

}