package br.com.lucad.kotlinmarmitex.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.ClickListener
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.Meal
import br.com.lucad.kotlinmarmitex.ui.views.PaymentActivity
import br.com.lucad.kotlinmarmitex.utils.Constants
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.lang.ref.WeakReference


class MealsAdapter(options: FirestoreRecyclerOptions<Meal>, private val listener: ClickListener, var listOfMeals: ArrayList<Meal>, var totalPrice: Double) :
    FirestoreRecyclerAdapter<Meal, MealsViewHolder>(options) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.meals_adapter_item, parent, false)
        return MealsViewHolder(view, listener)
    }

    override fun onBindViewHolder(
        holder: MealsViewHolder,
        position: Int,
        model: Meal
    ) {
        val textViewMealTitle: TextView = holder.itemView.findViewById(R.id.text_view_adapter_title)
        val textviewMealDescription: TextView =
            holder.itemView.findViewById(R.id.text_view_adapter_description)
        val imageViewMeal: ImageView = holder.itemView.findViewById(R.id.image_view_adapter_meal)
        val buttonOrder: Button = holder.itemView.findViewById(R.id.button_item_order_meal)
        val buttonAddCart: Button = holder.itemView.findViewById(R.id.button_item_order_add_cart)

        buttonOrder.setOnClickListener {
            val intent = Intent(it.context, PaymentActivity::class.java)
            intent.putExtra(Constants.MEALS_MODEL, model)
            it.context?.startActivity(intent)
        }

        buttonAddCart.setOnClickListener {
            listOfMeals.add(model)
            totalPrice += model.price
            Toast.makeText(it.context, "Item Adicionado: ${model.title}", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()
        }

        textViewMealTitle.text = model.title
        textviewMealDescription.text = model.description
        model.images?.get(0)?.let { getImageFromFirebase(holder.itemView, imageViewMeal, it) }
    }

    fun getListMeal(): ArrayList<Meal> {
        return listOfMeals
    }
    fun getTotal(): Double{
        return totalPrice
    }

    private fun getImageFromFirebase(itemView: View, imageView: ImageView, url: String) {
        Glide.with(itemView)
            .load(url)
            .centerCrop() //4
            .placeholder(R.drawable.ic_baseline_food_bank_24) //5
            .error(R.drawable.ic_baseline_image_not_supported_24) //6
            .into(imageView)
    }

}


class MealsViewHolder(itemView: View, listener: ClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {


    private var listenerRef: WeakReference<ClickListener> = WeakReference(listener)
    private var imageViewLike: ImageView = itemView.findViewById(R.id.image_view_item_like)
    private var imageViewDeslike: ImageView = itemView.findViewById(R.id.image_view_item_deslike)
    private var buttonOrder: Button = itemView.findViewById(R.id.button_item_order_meal)

    init {
        imageViewLike = itemView.findViewById(R.id.image_view_item_like)
        imageViewDeslike = itemView.findViewById(R.id.image_view_item_deslike)
        buttonOrder = itemView.findViewById(R.id.button_item_order_meal)

        itemView.setOnClickListener(this)
        imageViewLike.setOnClickListener(this)
        imageViewDeslike.setOnClickListener(this)
        buttonOrder.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        println("Fragment click")

        when (v!!.id) {
            imageViewLike.id -> {
                Toast.makeText(v.context, "Like PRESSED = $adapterPosition", Toast.LENGTH_SHORT).show()
            }
            imageViewDeslike.id -> {
                Toast.makeText(v.context, "Deslike PRESSED = $adapterPosition", Toast.LENGTH_SHORT).show()

            }
            buttonOrder.id -> {
                Toast.makeText(v.context, "Pedir = $adapterPosition", Toast.LENGTH_SHORT).show()
            }
        }
        listenerRef.get()?.onPositionClicked(adapterPosition)
    }

}

