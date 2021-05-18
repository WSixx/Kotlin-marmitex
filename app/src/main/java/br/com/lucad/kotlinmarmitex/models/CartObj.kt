package br.com.lucad.kotlinmarmitex.models

object CartObj {
    var mealsList: ArrayList<Meal> = ArrayList()
    var totalCart: Double = 0.0


    fun addCartItem(meal: Meal){
        mealsList.add(meal)
        totalCart += meal.price
        print("TOTAL::::$totalCart")
    }

    fun removeCartItem(position: Int){
        totalCart -= mealsList[position].price
        mealsList.removeAt(position)
    }
}