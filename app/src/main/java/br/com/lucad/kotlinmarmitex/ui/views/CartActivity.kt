package br.com.lucad.kotlinmarmitex.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import br.com.lucad.kotlinmarmitex.R

class CartActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        createActionToolbar()

        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    private fun createActionToolbar(){
        toolbar = findViewById(R.id.toolbar_carrinhos)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Carrinho"
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }

}