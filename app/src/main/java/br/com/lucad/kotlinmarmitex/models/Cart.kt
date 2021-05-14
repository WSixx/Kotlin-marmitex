package br.com.lucad.kotlinmarmitex.models

data class Cart(
    var id: String? = null,
    var mealsList: List<Meal>? = null,
    var quantity: Int? = null,
    var totalCart: Double? = null
){

}

class AddToCart(){



}
