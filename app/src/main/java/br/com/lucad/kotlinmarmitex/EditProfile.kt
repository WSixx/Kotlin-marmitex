package br.com.lucad.kotlinmarmitex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.models.User
import br.com.lucad.kotlinmarmitex.utils.Constants

class EditProfile : AppCompatActivity() {

    lateinit var editProfileNome: EditText
    lateinit var editProfileEmail: EditText
    private lateinit var editProfilePhone: EditText
    private lateinit var editProfileDDD: EditText
    private lateinit var editProfileCity: EditText
    private lateinit var editProfileUF: EditText
    private lateinit var editProfileDistrict: EditText
    private lateinit var editProfileStreet: EditText
    lateinit var buttonSave: Button

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        editProfileNome = findViewById(R.id.editText_edit_nome)
        editProfileEmail = findViewById(R.id.editText_edit_email)
        editProfilePhone = findViewById(R.id.editText_edit_phone)
        editProfileDDD = findViewById(R.id.editText_edit_phone_ddd)

        editProfileCity = findViewById(R.id.editText_edit_cidade)
        editProfileUF = findViewById(R.id.editText_edit_estado)
        editProfileDistrict = findViewById(R.id.editText_edit_bairro)
        editProfileStreet = findViewById(R.id.editText_edit_rua)

        buttonSave = findViewById(R.id.button_save_edit)

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            user = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }


        editProfileNome.isEnabled = false
        editProfileNome.setText(user?.username)
        editProfileEmail.isEnabled = false
        editProfileEmail.setText(user?.email)

        saveDataButton()

    }

    private fun saveDataButton(){
        buttonSave.setOnClickListener {
            if(checkIfPhoneIsEmpty()){
                toast("Dados Atualizados")
                val userHashMap = HashMap<String, Any>()
                var phone = editProfilePhone.text.toString().trim()

                userHashMap[Constants.PHONE] = phone
            }
        }
    }

    private fun checkIfPhoneIsEmpty(): Boolean{
        val phone = editProfilePhone.text
        val city = editProfileCity.text
        val street = editProfileStreet.text
        val uf = editProfileUF.text
        val ddd = editProfileDDD.text
        val district = editProfileDistrict.text
        return if(phone.isNullOrEmpty() || phone.isNullOrBlank() || phone.length < 9
            || city.isNullOrEmpty() || city.isNullOrBlank() || city.length < 3
            || street.isNullOrEmpty() || street.isNullOrBlank() || street.length < 5
            || uf.isNullOrEmpty() || uf.isNullOrBlank()
            || ddd.isNullOrEmpty() || ddd.isNullOrBlank()
            || district.isNullOrEmpty() || district.isNullOrBlank() || district.length < 4
        ){
            toast("Digite um Dados Válidos")
            false
        }else{
            true
        }
    }

}