package br.com.lucad.kotlinmarmitex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.Meal
import com.bumptech.glide.Glide

class CartAdapter(private val arrayOrders: ArrayList<Meal>, val context: Context) :
    RecyclerView.Adapter<ViewHolderCart>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCart {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cart_adapter_item, parent, false)
        return ViewHolderCart(view)
    }

    override fun onBindViewHolder(holder: ViewHolderCart, position: Int) {
        println(arrayOrders)
        arrayOrders[position].apply {
            holder.texViewTitleMealCart.text = this.title
            holder.texViewDescriptionMealCart.text = this.description
            holder.texViewPriceMealCart.text = "R$" + this.price.toString()
            this.images?.get(0)?.let {
                getImageFromFirebase(holder.itemView, holder.imageViewCart,
                    it
                )
            }
        }
    }

    private fun getImageFromFirebase(itemView: View, imageView: ImageView, url: String) {
        Glide.with(itemView)
            .load(url)
            .centerCrop() //4
            .placeholder(R.drawable.ic_baseline_food_bank_24) //5
            .error(R.drawable.ic_baseline_image_not_supported_24) //6
            .into(imageView)
    }

    override fun getItemCount(): Int {
        return arrayOrders.size
    }
}

class ViewHolderCart(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var texViewTitleMealCart: TextView = itemView.findViewById(R.id.text_view_adapter_cart_title)
    var texViewPriceMealCart: TextView = itemView.findViewById(R.id.text_view_adapter_cart_price)
    var texViewDescriptionMealCart: TextView = itemView.findViewById(R.id.text_view_adapter_cart_description)
    var imageViewCart: ImageView = itemView.findViewById(R.id.image_view_adapter_cart)


}