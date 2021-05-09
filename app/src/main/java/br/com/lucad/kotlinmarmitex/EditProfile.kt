package br.com.lucad.kotlinmarmitex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import br.com.lucad.kotlinmarmitex.models.User
import br.com.lucad.kotlinmarmitex.utils.Constants

class EditProfile : AppCompatActivity() {

    lateinit var editProfileNome: EditText
    lateinit var editProfileEmail: EditText
    lateinit var editProfilePhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        editProfileNome = findViewById(R.id.editText_edit_nome)
        editProfileEmail = findViewById(R.id.editText_edit_email)
        editProfilePhone = findViewById(R.id.editText_edit_phone)

        val user = intent.getParcelableExtra<User>(Constants.EXTRA_USER_DETAILS)

        editProfileNome.isEnabled = false
        editProfileNome.setText(user?.username)
        editProfileEmail.isEnabled = false
        editProfileEmail.setText(user?.email)

    }
}