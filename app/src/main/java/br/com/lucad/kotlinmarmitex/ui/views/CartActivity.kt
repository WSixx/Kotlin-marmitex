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
import kotlin.random.Random

class CartActivity : BaseActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listCart: RecyclerView
    private lateinit var adapter: CartAdapter
    lateinit var textViewTotal: TextView
    private lateinit var buttonFinalizar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        createActionToolbar()

        loadViews()
        loadAdapter()

        buttonFinalizar.setOnClickListener {
            makeOrder()
            finish()
        }

    }

    private fun loadAdapter(){
        listCart.layoutManager = LinearLayoutManager(this)

        adapter = CartAdapter(this)
        listCart.adapter = adapter
    }

    private fun loadViews(){
        textViewTotal = findViewById(R.id.text_view_cart_total)
        textViewTotal.text = "Total: R$%.2f".format(CartObj.totalCart)
        buttonFinalizar = findViewById(R.id.button_cart_finalizar)

        listCart = findViewById(R.id.recycle_cart)
    }

    private fun makeOrder() {

        val currentDate: String =
            SimpleDateFormat("dd/MM/yyyy às HH:mm:ss", Locale.getDefault()).format(Date())

        val order = Order()

        order.let {
            it.id = Random.nextInt(2000).toString()
            it.date = currentDate
            it.userId = SetUser().getCurrentUserId()
            it.meals = CartObj.mealsList
            it.total = CartObj.totalCart
        }

        Orders().registerOrder(this, order)

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