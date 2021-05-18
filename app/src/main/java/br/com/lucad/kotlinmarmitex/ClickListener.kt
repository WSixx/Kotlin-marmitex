package br.com.lucad.kotlinmarmitex

interface ClickListener {

    //TODO: MUDAR MEUS ADAPTER PARA USAR ESSA INTERFACE

    fun onPositionClicked(position: Int)

    fun onLongClicked(position: Int)
}