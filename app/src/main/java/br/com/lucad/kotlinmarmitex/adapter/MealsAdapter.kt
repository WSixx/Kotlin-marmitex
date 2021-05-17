package br.com.lucad.kotlinmarmitex.adapter

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
import br.com.lucad.kotlinmarmitex.models.CartObj
import br.com.lucad.kotlinmarmitex.models.Meal
import br.com.lucad.kotlinmarmitex.models.Meals
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
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
        val textViewMealDescription: TextView =
            holder.itemView.findViewById(R.id.text_view_adapter_description)
        val textViewMealPrice: TextView = holder.itemView.findViewById(R.id.text_view_adapter_price)
        val buttonAddCart: Button = holder.itemView.findViewById(R.id.button_item_order_add_cart)

        buttonAddCart.setOnClickListener {
            totalPrice += model.price
            Toast.makeText(it.context, "Item Adicionado: ${model.title}", Toast.LENGTH_SHORT).show()
            CartObj.addCartItem(model)
            notifyDataSetChanged()
        }

        textViewMealTitle.text = model.title
        textViewMealDescription.text = model.description
        textViewMealPrice.text = "R$${model.price}"


        val imageList = ArrayList<SlideModel>()
        for (images in model.images!!){
            imageList.add(SlideModel(images))
        }

        val imageSlider = holder.itemView.findViewById<ImageSlider>(R.id.image_view_adapter_meal)
        imageSlider.setImageList(imageList)

        //model.images?.get(0)?.let { LoadImage().getImageFromFirebase(holder.itemView, imageViewMeal, it) }
    }

}


class MealsViewHolder(itemView: View, listener: ClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {


    private var listenerRef: WeakReference<ClickListener> = WeakReference(listener)
    private var imageViewLike: ImageView = itemView.findViewById(R.id.image_view_item_like)
    private var imageViewDeslike: ImageView = itemView.findViewById(R.id.image_view_item_deslike)

    init {
        imageViewLike = itemView.findViewById(R.id.image_view_item_like)
        imageViewDeslike = itemView.findViewById(R.id.image_view_item_deslike)

        itemView.setOnClickListener(this)
        imageViewLike.setOnClickListener(this)
        imageViewDeslike.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            imageViewLike.id -> {
                Toast.makeText(v.context, "Like PRESSED = $adapterPosition", Toast.LENGTH_SHORT).show()
            }
            imageViewDeslike.id -> {
                Toast.makeText(v.context, "Deslike PRESSED = $adapterPosition", Toast.LENGTH_SHORT).show()

            }
        }
        listenerRef.get()?.onPositionClicked(adapterPosition)
    }

}

