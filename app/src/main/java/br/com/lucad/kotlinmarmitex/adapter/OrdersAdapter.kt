package br.com.lucad.kotlinmarmitex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.ClickListener
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.Order
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.lang.ref.WeakReference

class OrdersAdapter(options: FirestoreRecyclerOptions<Order>, private val listener: ClickListener) :
    FirestoreRecyclerAdapter<Order, OrdersViewHolder>(options) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.orders_adapter_item, parent, false)
        return OrdersViewHolder(view, listener)
    }

    override fun onBindViewHolder(
        holder: OrdersViewHolder,
        position: Int,
        model: Order
    ) {
        val textViewOrderMealData: TextView =
            holder.itemView.findViewById(R.id.text_view_adapter_order_data)
        val textviewOrderTotal: TextView =
            holder.itemView.findViewById(R.id.text_view_adapter_order_total)
        val imageViewOrder: ImageSlider =
            holder.itemView.findViewById(R.id.image_view_adapter_order)
        val buttonOrderDetail: Button = holder.itemView.findViewById(R.id.button_item_order_detail)

        buttonOrderDetail.setOnClickListener {
            /*  val intent = Intent(it.context, PaymentActivity::class.java)
              intent.putExtra(Constants.MEALS_MODEL, model)
              it.context?.startActivity(intent)*/
        }

        val total = "R$%.2f".format(model.total)

        textviewOrderTotal.text = total

        textViewOrderMealData.text = "Feito em: " + model.date

        val imageList = ArrayList<SlideModel>()
        for (meals in model.meals!!) {
            for (images in meals.images!!) {
                imageList.add(SlideModel(images))
            }
        }

        imageViewOrder.setImageList(imageList)

    }
}

class OrdersViewHolder(itemView: View, listener: ClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var listenerRef: WeakReference<ClickListener> = WeakReference(listener)
    private var buttonOrderDetail: Button = itemView.findViewById(R.id.button_item_order_detail)

    init {
        buttonOrderDetail = itemView.findViewById(R.id.button_item_order_detail)

        itemView.setOnClickListener(this)
        buttonOrderDetail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            buttonOrderDetail.id -> {
                Toast.makeText(v.context, "Detail = $adapterPosition", Toast.LENGTH_SHORT).show()
            }
        }
        listenerRef.get()?.onPositionClicked(adapterPosition)
    }

}

