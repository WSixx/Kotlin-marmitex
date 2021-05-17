package br.com.lucad.kotlinmarmitex.ui.views

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.adapter.CartAdapter
import br.com.lucad.kotlinmarmitex.models.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random


class CartActivity : BaseActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listCart: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var textViewTotal: TextView
    private lateinit var buttonFinalizar: Button
    private lateinit var quantityLessThanZero: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        createActionToolbar()

        loadViews()
        loadAdapter()

        if(CartObj.mealsList.isEmpty()){
            buttonFinalizar.isEnabled = false
        }

        buttonFinalizar.setOnClickListener {
            makeOrder()
        }

    }

    private fun loadAdapter() {
        listCart.layoutManager = LinearLayoutManager(this)

        adapter = CartAdapter(this)
        listCart.adapter = adapter
    }

    private fun loadViews() {
        textViewTotal = findViewById(R.id.text_view_cart_total)
        textViewTotal.text = "Total: R$%.2f".format(CartObj.totalCart)
        buttonFinalizar = findViewById(R.id.button_cart_finalizar)

        listCart = findViewById(R.id.recycle_cart)
    }

    private fun removeQuantityMeal(): Boolean {
        val mealsToRemove = Meals()
        var myReturn = false

        for (meals in CartObj.mealsList) {
            val myHashMeals = HashMap<String, Any>()
            val quantity = meals.quantity - 1
            if (quantity < 0) {
                myReturn = false
                quantityLessThanZero = meals.title!!
                break
            } else {
                myHashMeals["quantity"] = quantity
                mealsToRemove.updateMealQuantity(meals.id!!, myHashMeals)
                myReturn = true
            }
        }

        return myReturn
    }

    private fun makeOrder() {

        if (removeQuantityMeal()) {
            val currentDate: String =
                SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault()).format(Date())

            val order = Order()

            order.let {
                it.id = Random.nextInt(2000).toString()
                it.date = currentDate
                it.userId = SetUser().getCurrentUserId()
                it.meals = CartObj.mealsList
                it.total = CartObj.totalCart
            }

            Orders().registerOrder(order)
            clearAdapterData()
            finish()
        } else {
            showErrorSnack("Pedido não realizado $quantityLessThanZero não tem mais estoque", true)
        }
    }

    private fun clearAdapterData() {
        CartObj.mealsList.clear()
        CartObj.totalCart = 0.0
        adapter.notifyDataSetChanged()
    }

    private fun createActionToolbar() {
        toolbar = findViewById(R.id.toolbar_carrinhos)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Carrinho"
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}