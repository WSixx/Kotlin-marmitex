package br.com.lucad.kotlinmarmitex.ui.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.*
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import com.google.type.DateTime
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

class PaymentActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewPrice: TextView
    private lateinit var buttonPayment: Button

    private var meal: Meal = Meal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        textViewTitle = findViewById(R.id.text_view_title_payment)
        textViewDescription = findViewById(R.id.text_view_description_payment)
        textViewPrice = findViewById(R.id.text_view_price_payment)
        buttonPayment = findViewById(R.id.button_payment)

        if (intent.hasExtra(Constants.MEALS_MODEL)) {
            meal = intent.getParcelableExtra(Constants.MEALS_MODEL)!!
            Toast.makeText(this, "ENTROEU", Toast.LENGTH_LONG).show()
        }

        createActionToolbar()
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        buttonPayment.setOnClickListener {
            makeOrder()
        }


    }

    private fun makeOrder() {
        val orderHashMap = HashMap<String, Any>()
        val orderTitle = textViewTitle.text.toString().trim()
        val orderDescription = textViewDescription.text.toString().trim()
        val orderPrice = textViewPrice.text.toString().trim()

        val currentDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val order = Order()

        order.let {
            it.id = Random.nextInt(1000).toString()
            it.date = currentDateTime
            it.meals = meal
            it.total = meal.price
        }

        Orders().registerOrder(this, order)

    }

    private fun createActionToolbar() {
        toolbar = findViewById(R.id.toolbar_payment)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Pagamento"
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }

}