package br.com.lucad.kotlinmarmitex.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class MealsAdapter(options: FirestoreRecyclerOptions<Meal>) :
    FirestoreRecyclerAdapter<Meal, MealsViewHolder>(options) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meals_adapter_item, parent, false)
        return MealsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MealsViewHolder,
        position: Int,
        model: Meal
    ) {
        val textViewMealTitle: TextView = holder.itemView.findViewById(R.id.text_view_adapter_title)
        val textviewMealDescription: TextView = holder.itemView.findViewById(R.id.text_view_adapter_description)
        textViewMealTitle.text = model.title
        textviewMealDescription.text = model.description
    }
}


class MealsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private var textViewMealTitle: TextView = itemView.findViewById(R.id.text_view_adapter_title)
    private var textviewMealDescription: TextView = itemView.findViewById(R.id.text_view_adapter_description)
}

