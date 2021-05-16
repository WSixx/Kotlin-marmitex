package br.com.lucad.kotlinmarmitex.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import br.com.lucad.kotlinmarmitex.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    fun showErrorSnack(message: String, errorMessage: Boolean) {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if (errorMessage) {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.red_600
                )
            )
        } else {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.green
                )
            )
        }

        snackbar.show()
    }

}