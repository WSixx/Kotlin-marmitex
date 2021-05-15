package br.com.lucad.kotlinmarmitex.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.adapter.CartAdapter
import br.com.lucad.kotlinmarmitex.models.CartObj
import br.com.lucad.kotlinmarmitex.models.Meal
import br.com.lucad.kotlinmarmitex.utils.Constants

class CartActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listCart: RecyclerView
    private lateinit var adapter: CartAdapter
    lateinit var textViewTotal: TextView
    lateinit var buttonFinalizar: Button
    private var totalPrice: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        createActionToolbar()

        var listOfMeals = intent.getParcelableArrayListExtra<Meal>(Constants.GET_CART_ITEM)!!
        totalPrice = intent.getDoubleExtra(Constants.CART_TOTAL, 0.0)

        textViewTotal = findViewById(R.id.text_view_cart_total)
        textViewTotal.text = "Total: R$${CartObj.totalCart}"

        listCart = findViewById(R.id.recycle_cart)
        listCart.layoutManager = LinearLayoutManager(this)

        adapter = CartAdapter(listOfMeals, this)
        listCart.adapter = adapter

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun createActionToolbar() {
        toolbar = findViewById(R.id.toolbar_carrinhos)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Carrinho"
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }

}