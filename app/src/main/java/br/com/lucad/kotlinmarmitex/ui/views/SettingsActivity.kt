package br.com.lucad.kotlinmarmitex.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import br.com.lucad.kotlinmarmitex.MainActivity
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.SetUser
import br.com.lucad.kotlinmarmitex.models.User
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils

class SettingsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonSettingEdit: Button
    private lateinit var buttonSettingLogout: Button
    private lateinit var textViewUsername: TextView
    private lateinit var textViewEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        progressBar = findViewById(R.id.progress_settings)
        buttonSettingEdit = findViewById(R.id.button_settings_editar)
        buttonSettingLogout = findViewById(R.id.button_settings_logout)
        textViewUsername = findViewById(R.id.text_view_settings_username)
        textViewEmail = findViewById(R.id.text_view_settings_email)
        dataLoading()
        getUserDetails()

        createActionToolbar()

        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }

        buttonSettingLogout.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            try{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }finally {
                finishAffinity()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    private fun createActionToolbar(){
        toolbar = findViewById(R.id.toolbar_settings)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Configuracoes"
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }

    private fun getUserDetails(){
        SetUser().getUserInfo(this)

    }

    fun dataIsLoaded(user: User){
        hideProgressBar()
        buttonSettingEdit.isEnabled = true
        buttonSettingLogout.isEnabled = true

        textViewUsername.text = user.username
        textViewEmail.text = user.userCity

        buttonSettingEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        }

    }

    private fun dataLoading(){
        progressBar.visibility = View.VISIBLE
        buttonSettingEdit.isEnabled = false
        buttonSettingLogout.isEnabled = false
    }

    fun hideProgressBar(){
        progressBar.visibility = View.GONE

    }
}