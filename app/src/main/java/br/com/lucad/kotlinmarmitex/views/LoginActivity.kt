package br.com.lucad.kotlinmarmitex.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.lucad.kotlinmarmitex.MainActivity
import br.com.lucad.kotlinmarmitex.MyLoginFragment
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils.firebaseAuth
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils.firebaseUser
import com.google.firebase.auth.FirebaseUser
import io.github.cdimascio.dotenv.dotenv


val dotenv = dotenv {
    directory = "./assets/"
    filename = "env" // use filename env, instead of .env
} // <---- 1. Configure dotenv


class LoginActivity : AppCompatActivity() {

    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var editNome: EditText
    lateinit var texViewLogin: TextView
    lateinit var texViewCadastrar: TextView
    lateinit var buttonSignIn: Button
    lateinit var buttonSignUp: Button
    private lateinit var userEmail: String
    private lateinit var userPassword: String

    lateinit var createAccountInputsArray: Array<EditText>

    private val ev = dotenv["MY_EV"] // <---- 2. Get value from dotenv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignIn = findViewById<Button>(R.id.button_signin)
        buttonSignUp = findViewById<Button>(R.id.button_signup)
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        editNome = findViewById(R.id.edit_nome)
        texViewLogin = findViewById(R.id.text_view_login)
        texViewCadastrar = findViewById(R.id.textView_cadastrar)
        createAccountInputsArray = arrayOf(editEmail, editPassword)


        buttonSignIn.setOnClickListener {

            if(texViewLogin.isVisible){
                signIn()
            }else{
                //signUp()

            }
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
    private fun buttonCadastrarClick(){
        if(texViewCadastrar.text == "voltar"){
            texViewCadastrar.text = "Cadastrar-se"
            texViewLogin.text = "Login"
            buttonSignUp.visibility = View.GONE
            buttonSignIn.visibility = View.VISIBLE
            editNome.visibility = View.GONE

        }else{
            texViewCadastrar.text = "voltar"
            texViewLogin.text = "Cadastrar"
            buttonSignUp.visibility = View.VISIBLE
            buttonSignIn.visibility = View.GONE
            editNome.visibility = View.VISIBLE
        }
    }

    /* check if there's a signed-in user*/
    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            val currentUser = firebaseAuth.currentUser.uid
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", currentUser)
            startActivity(intent)
            toast("Bem Vindo")
        }
    }

    private fun signIn() {
        userEmail = editEmail.text.toString().trim()
        userPassword = editPassword.text.toString().trim()

        if (isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        val currentUser = firebaseAuth.currentUser.uid
                        var intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("user", currentUser)
                        startActivity(intent)


                        toast("Logado com Sucesso + $currentUser ++ ${firebaseAuth.currentUser.uid}")
                        finish()
                    } else {
                        //TODO: FAZER EXCEPTIONS
                        toast("Error ao Logar")
                    }
                }
        } else {
            toast("Digite os campos")
        }
    }

    open fun signUp() {
        userEmail = editEmail.text.toString().trim()
        userPassword = editPassword.text.toString().trim()

        if (isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("Conta criada com sucesso")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        //TODO: FAZER EXCEPTIONS
                        toast("Error ao criar usuario")
                    }
                }
        } else {
            toast("Digite os campos")
        }
    }

    private fun isNotEmpty(): Boolean = editEmail.text.toString().trim().isNotEmpty() &&
            editPassword.text.toString().trim().isNotEmpty()

}