package br.com.lucad.kotlinmarmitex.extensions

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import br.com.lucad.kotlinmarmitex.R
import com.google.android.material.snackbar.Snackbar


object Extensions {

    fun Activity.sucessSnackBar(msg: String, view: View, anchorView: View){
        val mySnack = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        mySnack.anchorView = anchorView
        mySnack.setBackgroundTint(ContextCompat.getColor(this, R.color.green))
        mySnack.show()
    }

    fun Activity.errorSnackBar(msg: String, view:  View, anchorView: View){
        val mySnack = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        mySnack.anchorView = anchorView
        mySnack.setBackgroundTint(ContextCompat.getColor(this, R.color.red_400))
        mySnack.show()
    }
}