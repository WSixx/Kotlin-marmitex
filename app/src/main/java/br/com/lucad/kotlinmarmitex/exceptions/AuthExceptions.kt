package br.com.lucad.kotlinmarmitex.exceptions

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthExceptions {

    fun handleException(exception: Exception?): String {
        exception?.let {
            return when (it) {
                is FirebaseAuthUserCollisionException -> "E-mail já cadastrado"
                is FirebaseNetworkException -> "Operação não permitida, tente mais tarde!"
                is FirebaseTooManyRequestsException -> "Muitas tentativas tente mais tarde!"
                is FirebaseAuthEmailException -> "Error ao cadastrar E-mail"
                is FirebaseAuthWeakPasswordException -> "Senha muito fraca. Tente outra"
                else -> "Tente mais tarde!"
            }
        }
        return "Erro desconhecido"
    }

}
