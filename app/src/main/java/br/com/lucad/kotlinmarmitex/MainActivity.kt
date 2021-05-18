package br.com.lucad.kotlinmarmitex

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils

//TODO: NO FINAL ESTUDAR O ANDROID MVVM -> SE POSSIVEL COLOCAR AQUI

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences:SharedPreferences
    lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMySharedPreferencesData()

        val textView = findViewById<TextView>(R.id.texte_view_teste)
        val buttonMain = findViewById<Button>(R.id.button_main)

        title = username
        textView.text = username

        buttonMain.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            finish()
        }
    }

    private fun getMySharedPreferencesData(){
        sharedPreferences = getSharedPreferences(Constants.MY_SHARE_PREF, Context.MODE_PRIVATE)
        username = sharedPreferences.getString(Constants.USERNAME_LOGGED, "")!!
    }
}