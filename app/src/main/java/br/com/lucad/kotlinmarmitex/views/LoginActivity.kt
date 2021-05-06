package br.com.lucad.kotlinmarmitex.views

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import br.com.lucad.kotlinmarmitex.MyLoginFragment
import br.com.lucad.kotlinmarmitex.R
import br.com.lucad.kotlinmarmitex.models.Auth
import io.github.cdimascio.dotenv.dotenv


val dotenv = dotenv {
    directory = "./assets/"
    filename = "env" // use filename env, instead of .env
} // <---- 1. Configure dotenv


class LoginActivity : AppCompatActivity() {

    private val ev = dotenv["MY_EV"] // <---- 2. Get value from dotenv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        Toast.makeText(this, "Valor da $ev", Toast.LENGTH_LONG).show()

        val buttonLogin = findViewById<Button>(R.id.button_login)

        buttonLogin.setOnClickListener{
            MyLoginFragment.newInstance("Title", "SubTitle").show(supportFragmentManager, MyLoginFragment.TAG)

        }

    }

    public override fun onStart() {
        super.onStart()
        val auth = Auth()
        if(auth.checkIsLogin()) {
            Toast.makeText(this, "Esta Logado", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Não está logado", Toast.LENGTH_LONG).show()
        }
    }

}