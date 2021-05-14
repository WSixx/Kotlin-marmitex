package br.com.lucad.kotlinmarmitex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.models.Meal

class CartAdapter(val arrayOrders: ArrayList<Meal>, val context:Context): RecyclerView.Adapter<ViewHolderCart>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCart {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate()
    }

    override fun onBindViewHolder(holder: ViewHolderCart, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}


class ViewHolderCart(itemView: View): RecyclerView.ViewHolder(itemView){}