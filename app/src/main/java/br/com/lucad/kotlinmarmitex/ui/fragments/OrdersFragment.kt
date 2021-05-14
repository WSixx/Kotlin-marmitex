package br.com.lucad.kotlinmarmitex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.ClickListener
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.adapter.OrdersAdapter
import br.com.lucad.kotlinmarmitex.models.*
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class OrdersFragment : Fragment() {

    private lateinit var myRecycle: RecyclerView
    lateinit var adapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        myRecycle = requireView().findViewById(R.id.recycle_orders)
        super.onViewCreated(itemView, savedInstanceState)

        val query: Query = FirebaseUtils.firebaseFirestore.collection(Constants.ORDERS).whereEqualTo("userId",SetUser().getCurrentUserId())
        val options = FirestoreRecyclerOptions.Builder<Order>().setQuery(query, Order::class.java).setLifecycleOwner(this).build()

        adapter = OrdersAdapter(options, object : ClickListener {
            override fun onPositionClicked(position: Int) {

            }
            override fun onLongClicked(position: Int) {
                // callback performed on click
            }
        })

        myRecycle.adapter = adapter
        myRecycle.layoutManager = LinearLayoutManager(activity)

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}