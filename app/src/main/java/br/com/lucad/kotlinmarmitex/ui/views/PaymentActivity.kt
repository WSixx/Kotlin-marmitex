package br.com.lucad.kotlinmarmitex.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.*
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.LoadImage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class PaymentActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewPrice: TextView
    private lateinit var imageViewMeal: ImageView
    private lateinit var buttonPayment: Button

    private var meal: Meal = Meal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        textViewTitle = findViewById(R.id.text_view_title_payment)
        textViewDescription = findViewById(R.id.text_view_description_payment)
        textViewPrice = findViewById(R.id.text_view_price_payment)
        imageViewMeal = findViewById(R.id.image_view_payment)
        buttonPayment = findViewById(R.id.button_payment)

        if (intent.hasExtra(Constants.MEALS_MODEL)) {
            meal = intent.getParcelableExtra(Constants.MEALS_MODEL)!!
            textViewTitle.text = meal.title
            textViewDescription.text = meal.description
            textViewPrice.text = meal.price.toString()

            meal.images?.get(0)?.let {
                LoadImage().getImageFromFirebase(imageViewMeal, imageViewMeal,
                    it
                )
            }
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

        val currentDate: String =
            SimpleDateFormat("dd/MM/yyyy às HH:mm:ss", Locale.getDefault()).format(Date())

        val order = Order()

        order.let {
            it.id = Random.nextInt(2000).toString()
            it.date = currentDate
            it.userId = SetUser().getCurrentUserId()
            it.meals = ArrayList()
            it.total = meal.price
        }

       // Orders().registerOrder(this, order, )

    }

    private fun createActionToolbar() {
        toolbar = findViewById(R.id.toolbar_payment)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Pagamento"
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }

}