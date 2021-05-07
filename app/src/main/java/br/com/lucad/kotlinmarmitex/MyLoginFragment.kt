package br.com.lucad.kotlinmarmitex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import br.com.lucad.kotlinmarmitex.views.LoginActivity

class MyLoginFragment : DialogFragment() {

    private lateinit var viewOfLayout: View

    companion object {

        const val TAG = "SimpleDialog"

        fun newInstance(): MyLoginFragment {
            return MyLoginFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewOfLayout = inflater.inflate(R.layout.mylogin_fragment, container, false)
        viewOfLayout.findViewById<Button>(R.id.button_fragment_login)
        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners(view: View) {
        view.findViewById<Button>(R.id.button_fragment_login).setOnClickListener {
            val editEmail = view.findViewById<EditText>(R.id.edit_fragment_user)
            val editPassword = view.findViewById<EditText>(R.id.edit_fragment_password)

            Toast.makeText(
                it.context,
                "${editEmail.text} ${editPassword.text}",
                Toast.LENGTH_SHORT
            ).show()
            //val login = LoginActivity()
           // login.signUp(editEmail.text.toString(), editPassword.text.toString())
            dismiss()
        }
    }


}