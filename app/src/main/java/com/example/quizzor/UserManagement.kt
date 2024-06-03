package com.example.quizzor

import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigInteger
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

class UserManagement {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun registerUser(username: String, password: String){
        val username = hashMapOf(
            "username" to username,
            "password" to hashString(password, "MD5"),
        )

        db.collection("users").add(username)
            //.set(username)
            //.addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            //.addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }

    fun signIn(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        db.collection("users").get().addOnSuccessListener { documents ->
            for (document in documents){
                val u = document.get("username")
                val p = document.get("password")
                if (u == username && p == hashString(password, "MD5")){
                    callback(true, u.toString())
                    break
                }
            }
            callback(false, null)
        }

    }

    fun hashString(str: String, algorithm: String): String {
        val res = MessageDigest.getInstance(algorithm).digest(str.toByteArray(UTF_8))
        val bigInt = BigInteger(1, res)
        return String.format("%032x", bigInt)
    }
}