package br.com.lucad.kotlinmarmitex.extensions

import android.app.Activity
import android.widget.Toast

object Extensions {

    //TODO: MUDAR PARA SNACK BAR ?TALVEZ?
    // COLOCAR UMA PARA ERROR - VERMELHO
    // OUTRA PARA SUCCESSFUL - AZUL OU VERDE

    fun Activity.toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}