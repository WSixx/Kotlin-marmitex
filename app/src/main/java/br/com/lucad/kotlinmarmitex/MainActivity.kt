package br.com.lucad.kotlinmarmitex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.models.Meals
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = FirebaseUtils.firebaseAuth.currentUser.displayName

        val textView = findViewById<TextView>(R.id.texte_view_teste)
        val buttonMain = findViewById<Button>(R.id.button_main)



        title = currentUser
        toast("Logado com Sucesso + $currentUser")
        textView.text = currentUser

        buttonMain.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            finish()
        }

    }
}