package br.com.lucad.kotlinmarmitex.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.exceptions.AuthExceptions
import br.com.lucad.kotlinmarmitex.models.SetUser
import br.com.lucad.kotlinmarmitex.models.User
import br.com.lucad.kotlinmarmitex.utils.Constants
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.github.cdimascio.dotenv.dotenv


val dotenv = dotenv {
    directory = "./assets/"
    filename = "env" // use filename env, instead of .env
} // <---- 1. Configure dotenv


class LoginActivity : BaseActivity() {

    private lateinit var activityLogin: View
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editPasswordSecondary: EditText
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

        hideSystemUI()

        loadViews()

        createAccountInputsArray = arrayOf(editEmail, editPassword)

        loadClicks()

    }

    private fun loadViews() {
        activityLogin = findViewById(R.id.activity_login)

        buttonSignIn = findViewById(R.id.button_signin)
        buttonSignUp = findViewById(R.id.button_signup)

        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        editPasswordSecondary = findViewById(R.id.edit_password_secondary)
        editNomeUser = findViewById(R.id.edit_user_nome)

        texViewLogin = findViewById(R.id.text_view_login)
        texViewCadastrar = findViewById(R.id.textView_cadastrar)

        progressLogin = findViewById(R.id.progress_login)
    }

    private fun loadClicks() {
        buttonSignIn.setOnClickListener {
            if (texViewLogin.isVisible) signIn()
        }

        texViewCadastrar.setOnClickListener {
            buttonCadastrarClick()
        }

        buttonSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun buttonCadastrarClick() {
        if (texViewCadastrar.text == getString(R.string.voltar_text)) {
            texViewCadastrar.text = getString(R.string.casdatrar_text)
            texViewLogin.text = getString(R.string.login_text)
            buttonSignUp.visibility = View.GONE
            buttonSignIn.visibility = View.VISIBLE
            editPasswordSecondary.visibility = View.GONE
            editNomeUser.visibility = View.GONE

        } else {
            texViewCadastrar.text = getString(R.string.voltar_text)
            texViewLogin.text = getString(R.string.casdatrar_text)
            buttonSignUp.visibility = View.VISIBLE
            editPasswordSecondary.visibility = View.VISIBLE
            buttonSignIn.visibility = View.GONE
            editNomeUser.visibility = View.VISIBLE
        }
    }

    /* check if there's a signed-in user*/
    override fun onStart() {
        super.onStart()
        val fireUser: FirebaseUser? = firebaseAuth.currentUser
        fireUser?.let {
            SetUser().getUserInfo(this@LoginActivity)
        }
    }

    private fun signIn() {
        showMyProgressBar()
        userEmail = editEmail.text.toString().trim()
        userPassword = editPassword.text.toString().trim()

        if (isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        SetUser().getUserInfo(this@LoginActivity)
                    } else {
                        showErrorSnack("Error ao logar", true)
                        hideMyProgressBar()
                    }
                }
        } else {
            userBlankFields()
            hideMyProgressBar()
        }
    }

    private fun signUp() {
        userEmail = editEmail.text.toString().trim()
        userPassword = editPassword.text.toString().trim()
        userName = editNomeUser.text.toString().trim()

        showMyProgressBar()

        when (false) {
            isNotEmpty() -> {
                userBlankFields()
                hideMyProgressBar()
                return
            }
            passwordIsEqual() -> {
                showErrorSnack("Senhas precisam ser iguais", true)
                editPasswordSecondary.text.clear()
                hideMyProgressBar()
                return
            }
            emailValidate() -> {
                showErrorSnack("Digite um email válido!!", true)
                hideMyProgressBar()
                return
            }
        }
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
                    userIsLogged(user)
                    finish()
                }
            }

            .addOnFailureListener { exception ->
                showErrorSnack(AuthExceptions().handleException(exception), true)
                hideMyProgressBar()
            }

    }

    fun userIsLogged(user: User?) {
        if (user?.profileIsComplete == 0) {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        finishAffinity()
    }

    private fun userBlankFields() {
        showErrorSnack(Constants.BLANK_FIELD, true)
    }

    private fun emailValidate(): Boolean {
        val regex = Regex("/^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+\\.([a-z]+)?\$/i\n")
        return !regex.matches(editEmail.text.toString())
    }

    private fun passwordIsEqual(): Boolean {
        return editPassword.text.toString().trim() == editPasswordSecondary.text.toString().trim()
    }

    private fun isNotEmpty(): Boolean = editEmail.text.toString().trim().isNotEmpty() &&
            editPassword.text.toString().trim().isNotEmpty()
}