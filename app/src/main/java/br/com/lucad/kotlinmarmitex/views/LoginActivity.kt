package br.com.lucad.kotlinmarmitex.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.lucad.kotlinmarmitex.MainActivity
import br.com.lucad.kotlinmarmitex.MyLoginFragment
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.extensions.Extensions.toast
import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.github.cdimascio.dotenv.dotenv


val dotenv = dotenv {
    directory = "./assets/"
    filename = "env" // use filename env, instead of .env
} // <---- 1. Configure dotenv


class LoginActivity : AppCompatActivity() {

    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    private lateinit var userEmail: String
    private lateinit var userPassword: String

    lateinit var createAccountInputsArray: Array<EditText>

    private val ev = dotenv["MY_EV"] // <---- 2. Get value from dotenv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonSignIn = findViewById<Button>(R.id.button_signin)
        val buttonSignUp = findViewById<Button>(R.id.button_signup)
        editEmail = findViewById<EditText>(R.id.edit_email)
        editPassword = findViewById<EditText>(R.id.edit_password)
        createAccountInputsArray = arrayOf(editEmail, editPassword)


        buttonSignIn.setOnClickListener {
            signIn()
        }


        buttonSignUp.setOnClickListener{
            MyLoginFragment.newInstance("Title", "SubTitle").show(supportFragmentManager, MyLoginFragment.TAG)
        }

    }

    /* check if there's a signed-in user*/
    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, MainActivity::class.java))
            toast("Bem Vindo")
        }
    }

    private fun signIn() {
        userEmail = editEmail.text.toString().trim()
        userPassword = editPassword.text.toString().trim()

        if(isNotEmpty()){
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        toast("Logado com Sucesso")
                        finish()
                    } else {
                        //TODO: FAZER EXCEPTIONS
                        toast("Error ao Logar")
                    }
                }
                .addOnSuccessListener {
                    toast("Sucesso")
                }
        }else{
            toast("Digite os campos")
        }
    }

    private fun signUp() {
        userEmail = editEmail.text.toString().trim()
        userPassword = editPassword.text.toString().trim()

        if(isNotEmpty()){
            /*create a user*/
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
        }else{
            toast("Digite os campos")
        }
    }

    private fun isNotEmpty(): Boolean = editEmail.text.toString().trim().isNotEmpty() &&
            editPassword.text.toString().trim().isNotEmpty() &&
            editPassword.text.toString().trim().isNotEmpty()

}