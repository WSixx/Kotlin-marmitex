package br.com.lucad.kotlinmarmitex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.lucad.kotlinmarmitex.R

class OrdersFragment : Fragment() {

    //private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_orders, container, false)

        val textView: TextView = root.findViewById(R.id.text_orders)
            textView.text = "OrdersFragemnt"
        return root
    }
}