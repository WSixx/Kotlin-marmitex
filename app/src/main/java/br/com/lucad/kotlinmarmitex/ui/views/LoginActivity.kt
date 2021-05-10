package br.com.lucad.kotlinmarmitex.views.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.models.SetUser
import br.com.lucad.kotlinmarmitex.models.User
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils.firebaseAuth
import io.github.cdimascio.dotenv.dotenv


val dotenv = dotenv {
    directory = "./assets/"
    filename = "env" // use filename env, instead of .env
} // <---- 1. Configure dotenv


class LoginActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editNomeUser: EditText
    private lateinit var texViewLogin: TextView
    private lateinit var texViewCadastrar: TextView
    private lateinit var buttonSignIn: Button
    private lateinit var buttonSignUp: Button
    private lateinit var progressLogin: ProgressBar
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var userName: String

    private lateinit var createAccountInputsArray: Array<EditText>

    private val ev = dotenv["MY_EV"] // <---- 2. Get value from dotenv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignIn = findViewById(R.id.button_signin)
        buttonSignUp = findViewById(R.id.button_signup)

        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        editNomeUser = findViewById(R.id.edit_user_nome)

        texViewLogin = findViewById(R.id.text_view_login)
        texViewCadastrar = findViewById(R.id.textView_cadastrar)

        progressLogin = findViewById(R.id.progress_login)

        createAccountInputsArray = arrayOf(editEmail, editPassword)


        buttonSignIn.setOnClickListener {
            if (texViewLogin.isVisible) signIn()
        }

        texViewCadastrar.setOnClickListener {
            buttonCadastrarClick()
        }

        buttonSignUp.setOnClickListener {
            //MyLoginFragment.newInstance().show(supportFragmentManager, MyLoginFragment.TAG)
            signUp()
        }
    }

    //teste
    private fun buttonCadastrarClick() {
        if (texViewCadastrar.text == "voltar") {
            texViewCadastrar.text = "Cadastrar-se"
            texViewLogin.text = "Login"
            buttonSignUp.visibility = View.GONE
            buttonSignIn.visibility = View.VISIBLE
            editNomeUser.visibility = View.GONE

        } else {
            texViewCadastrar.text = "voltar"
            texViewLogin.text = "Cadastrar"
            buttonSignUp.visibility = View.VISIBLE
            buttonSignIn.visibility = View.GONE
            editNomeUser.visibility = View.VISIBLE
        }
    }

    /* check if there's a signed-in user*/
    override fun onStart() {
        super.onStart()
        /*val user = User()
        userIsLogged(user)*/
       /* val fireUser: FirebaseUser? = firebaseAuth.currentUser
        val user = User()
        fireUser?.let {
            userIsLogged(user)
        }*/
    }

    private fun signIn() {
        showProgressLogin()
        userEmail = editEmail.text.toString().trim()
        userPassword = editPassword.text.toString().trim()

        if (isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        SetUser().getUserInfo(this@LoginActivity)
                    } else {
                        toast("Error ao Logar")
                        hideProgressLogin()
                    }
                }
        } else {
            userBlankFields()
            hideProgressLogin()
        }
    }

    private fun signUp() {
        userEmail = editEmail.text.toString().trim()
        userPassword = editPassword.text.toString().trim()
        userName = editNomeUser.text.toString().trim()

        showProgressLogin()

        if (isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val setUser = SetUser()
                        val user = User()
                        val currentUserUID = firebaseAuth.currentUser?.uid

                        user.let {
                            it.id = currentUserUID
                            it.username = userName
                            it.email = userEmail
                        }

                        setUser.registerUser(this@LoginActivity, user)
                        setUser.writeNewUser(user)
                        userIsLogged(user)
                        finish()
                    } else {
                        //TODO: FAZER EXCEPTIONS
                        toast("Error ao criar usuario")
                        hideProgressLogin()
                    }
                }
        } else {
            userBlankFields()
            hideProgressLogin()
        }
    }

    fun userIsLogged(user: User?) {
        hideProgressLogin()

        if(user?.profileIsComplete == 0){
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        }else{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

    fun userRegisterSuccessful() {
        toast("Conta criada com sucesso")
    }

    private fun userBlankFields() {
        toast(Constants.BLANK_FIELD)
    }

    fun hideProgressLogin() {
        progressLogin.visibility = View.GONE
    }

    fun showProgressLogin() {
        progressLogin.visibility = View.VISIBLE
    }


    private fun isNotEmpty(): Boolean = editEmail.text.toString().trim().isNotEmpty() &&
            editPassword.text.toString().trim().isNotEmpty()

}