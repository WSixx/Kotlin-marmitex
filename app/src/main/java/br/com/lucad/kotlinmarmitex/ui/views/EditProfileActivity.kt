package br.com.lucad.kotlinmarmitex.ui.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.SetUser
import br.com.lucad.kotlinmarmitex.models.User
import br.com.lucad.kotlinmarmitex.utils.Constants

class EditProfileActivity : BaseActivity() {

    private lateinit var editProfileNome: EditText
    private lateinit var editProfileEmail: EditText
    private lateinit var editProfilePhone: EditText
    private lateinit var editProfileDDD: EditText
    private lateinit var editProfileCity: EditText
    private lateinit var editProfileUF: EditText
    private lateinit var editProfileDistrict: EditText
    private lateinit var editProfileStreet: EditText
    lateinit var buttonSave: Button
    private lateinit var progressEdit: ProgressBar
    private lateinit var toolbar: Toolbar

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        loadViews()

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            user = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        editProfileNome.isEnabled = false
        editProfileNome.setText(user.username)
        editProfileEmail.isEnabled = false
        editProfileEmail.setText(user.email)

        checkIfIntentHasAllExtra()

        saveDataButton()

        createActionToolbar()

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun checkIfIntentHasAllExtra(){
        if (user.userCity.isNotEmpty()) editProfileCity.setText(user.userCity) else ""
        if (user.userPhone.isNotEmpty()) editProfilePhone.setText(user.userPhone) else ""
        if (user.userDdd.isNotEmpty()) editProfileDDD.setText(user.userDdd) else ""
        if (user.userDistrict.isNotEmpty()) editProfileDistrict.setText(user.userDistrict) else ""
        if (user.userUF.isNotEmpty()) editProfileUF.setText(user.userUF) else ""
        if (user.userStreet.isNotEmpty()) editProfileStreet.setText(user.userStreet) else ""
    }

    private fun loadViews() {
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
    }

    private fun updateUserProfileDetails() {
        if (checkIfPhoneIsEmpty()) {
            showMyProgressBar()
            buttonSave.isEnabled = false
            val userHashMap = HashMap<String, Any>()
            val phone = editProfilePhone.text.toString().trim()
            val ddd = editProfileDDD.text.toString().trim()
            val city = editProfileCity.text.toString().trim()
            val uf = editProfileUF.text.toString().trim()
            val street = editProfileStreet.text.toString().trim()
            val district = editProfileDistrict.text.toString().trim()

            userHashMap[Constants.USER_PHONE] = phone
            userHashMap[Constants.USER_DDD] = ddd
            userHashMap[Constants.USER_CITY] = city
            userHashMap[Constants.USER_UF] = uf
            userHashMap[Constants.USER_DISTRICT] = district
            userHashMap[Constants.USER_STREET] = street
            userHashMap[Constants.PROFILE_IS_COMPLETE] = 1

            SetUser().updateUserInfo(this, userHashMap)
        }
    }

    private fun saveDataButton() {

        buttonSave.setOnClickListener {
            updateUserProfileDetails()
        }
    }


    private fun createActionToolbar() {
        toolbar = findViewById(R.id.toolbar_editar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Editar"
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }


    private fun checkIfPhoneIsEmpty(): Boolean {
        val phone = editProfilePhone.text.toString().trim()
        val city = editProfileCity.text.toString().trim()
        val street = editProfileStreet.text.toString().trim()
        val uf = editProfileUF.text.toString().trim()
        val ddd = editProfileDDD.text.toString().trim()
        val district = editProfileDistrict.text.toString().trim()
        return if (phone.isEmpty() || phone.isBlank() || phone.length < 9
            || city.isEmpty() || city.isBlank() || city.length < 3
            || street.isEmpty() || street.isBlank() || street.length < 5
            || uf.isEmpty() || uf.isBlank()
            || ddd.isEmpty() || ddd.isBlank()
            || district.isEmpty() || district.isBlank() || district.length < 4
        ) {
            showErrorSnack("Digite um Dados Válidos", true)
            false
        } else {
            true
        }
    }

    fun afterSaveNewInformationSuccessful() {
        hideMyProgressBar()
        showErrorSnack(
            "Sucesso ao atualizar Usuario",
            false
        )
        buttonSave.isEnabled = true

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}