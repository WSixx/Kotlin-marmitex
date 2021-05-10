package br.com.lucad.kotlinmarmitex.views.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.lucad.kotlinmarmitex.R

class PedidosFragment : Fragment() {

    // private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_pedidos, container, false)

        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.text = "DashBoard"

        return root
    }

}