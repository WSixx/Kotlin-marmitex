package br.com.lucad.kotlinmarmitex.views.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import br.com.lucad.kotlinmarmitex.MainActivity
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.models.SetUser
import br.com.lucad.kotlinmarmitex.models.User
import br.com.lucad.kotlinmarmitex.utils.Constants

class EditProfileActivity : AppCompatActivity() {

    lateinit var editProfileNome: EditText
    lateinit var editProfileEmail: EditText
    private lateinit var editProfilePhone: EditText
    private lateinit var editProfileDDD: EditText
    private lateinit var editProfileCity: EditText
    private lateinit var editProfileUF: EditText
    private lateinit var editProfileDistrict: EditText
    private lateinit var editProfileStreet: EditText
    lateinit var buttonSave: Button
    lateinit var progressEdit: ProgressBar

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

        progressEdit = findViewById(R.id.progressBar_edit)

        buttonSave = findViewById(R.id.button_save_edit)

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            user = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }


        editProfileNome.isEnabled = false
        editProfileNome.setText(user.username)
        editProfileEmail.isEnabled = false
        editProfileEmail.setText(user.email)

        saveDataButton()

    }

    private fun updateUserProfileDetails(){
        if(checkIfPhoneIsEmpty()){
            progressEdit.visibility = View.VISIBLE
            buttonSave.isEnabled = false
            val userHashMap = HashMap<String, Any>()
            val phone = editProfilePhone.text.toString().trim()
            val ddd = editProfileDDD.text.toString().trim()
            val city = editProfileCity.text.toString().trim()
            val uf = editProfileUF.text.toString().trim()
            val street = editProfileStreet.text.toString().trim()
            val district = editProfileDistrict.text.toString().trim()

            userHashMap[Constants.PHONE] = phone
            userHashMap[Constants.DDD] = ddd
            userHashMap[Constants.CITY] = city
            userHashMap[Constants.UF] = uf
            userHashMap[Constants.DISTRICT] = district
            userHashMap[Constants.STREET] = street
            userHashMap[Constants.PROFILE_IS_COMPLETE] = 1

            SetUser().updateUserInfo(this, userHashMap )
        }
    }

    private fun saveDataButton(){

        buttonSave.setOnClickListener {
           updateUserProfileDetails()
        }
    }



    private fun checkIfPhoneIsEmpty(): Boolean{
        val phone = editProfilePhone.text.toString().trim()
        val city = editProfileCity.text.toString().trim()
        val street = editProfileStreet.text.toString().trim()
        val uf = editProfileUF.text.toString().trim()
        val ddd = editProfileDDD.text.toString().trim()
        val district = editProfileDistrict.text.toString().trim()
        return if(phone.isEmpty() || phone.isBlank() || phone.length < 9
            || city.isEmpty() || city.isBlank() || city.length < 3
            || street.isEmpty() || street.isBlank() || street.length < 5
            || uf.isEmpty() || uf.isBlank()
            || ddd.isEmpty() || ddd.isBlank()
            || district.isEmpty() || district.isBlank() || district.length < 4
        ){
            toast("Digite um Dados Válidos")
            false
        }else{
            true
        }
    }

    fun afterSaveNewInformationSuccessful(){
        hideProgressEdit()
        toast("Sucesso ao atualizar Usuario - Edit")
        buttonSave.isEnabled = true

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun hideProgressEdit() {
        progressEdit.visibility = View.GONE
    }

}