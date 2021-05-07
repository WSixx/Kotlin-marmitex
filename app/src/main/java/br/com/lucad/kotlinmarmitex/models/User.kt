package br.com.lucad.kotlinmarmitex.models

import br.com.lucad.kotlinmarmitex.utils.FirebaseUtils

data class User(val username: String? = null, val email: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.


}