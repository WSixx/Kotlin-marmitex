package br.com.lucad.kotlinmarmitex.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lucad.kotlinmarmitex.ClickListener
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.Meal
import br.com.lucad.kotlinmarmitex.models.MealsAdapter
import br.com.lucad.kotlinmarmitex.ui.views.SettingsActivity
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query


class HomeFragment : Fragment() {

    private lateinit var myRecycle: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        myRecycle = requireView().findViewById(R.id.recycle_meals)


        super.onViewCreated(itemView, savedInstanceState)
        val query: Query = FirebaseUtils.firebaseFirestore.collection(Constants.MEALS)
        val options = FirestoreRecyclerOptions.Builder<Meal>().setQuery(query, Meal::class.java)
            .setLifecycleOwner(this).build()

        val adapter = MealsAdapter(options, object : ClickListener {
            override fun onPositionClicked(position: Int) {
                println("Fragment click")
            }

            override fun onLongClicked(position: Int) {
                // callback performed on click
            }
        })

        myRecycle.adapter = adapter
        myRecycle.layoutManager = LinearLayoutManager(activity)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.action_settings -> {
                val intent = Intent(activity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}