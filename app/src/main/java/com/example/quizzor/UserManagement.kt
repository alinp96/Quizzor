package com.example.quizzor

import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigInteger
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class UserManagement {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var loggedInUserId: String? = null
    private var loggedInUsername: String? = null

    suspend fun registerUser(nickname: String, mUsername: String, password: String): Char{
        var returnValue: Char = '0'
        val username = hashMapOf(
            "nickname" to nickname,
            "username" to mUsername,
            "password" to hashString(password, "MD5"),
            "score" to hashMapOf(
                "beverages" to 0,
                "biology" to 0,
                "booksandliterature" to 0,
                "celebrityandpopculture" to 0,
                "cheistry" to 0,
                "computerscience" to 0,
                "countriesandcapitals" to 0,
                "cuisine" to 0,
                "famoushistoricalfigures" to 0,
                "famouspersonalities" to 0,
                "foodandtrivia" to 0,
                "geographicfactsandtrivia" to 0,
                "historicalevents" to 0,
                "ingredients" to 0,
                "memorablesportingevents" to 0,
                "movies" to 0,
                "music" to 0,
                "physics" to 0,
                "soccer" to 0,
                "trivia" to 0,
                "tvshows" to 0,
                "unusualsportsfacts" to 0,
                "videogames" to 0,
                "worldfacts" to 0,
            )
        )
        var userMatchFound: Boolean = false
        var nicknameMatchFound: Boolean = false

        try {
            val documents: QuerySnapshot = db.collection("users").get().await()
            for (document in documents) {
                if (nickname.lowercase() == document.get("nickname").toString().lowercase()) {
                    nicknameMatchFound = true
                    break
                }
                if (mUsername.lowercase() == document.get("username").toString().lowercase()) {
                    userMatchFound = true
                    break
                }
            }

            if (!userMatchFound && !nicknameMatchFound) {
                db.collection("users").add(username).await()
                returnValue = '1'
            } else {
                if (userMatchFound) {
                    returnValue = 'u'
                }
                if (nicknameMatchFound) {
                    returnValue = 'n'
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the exception as needed
        }

        return returnValue
    }

    fun signIn(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        db.collection("users").get().addOnSuccessListener { documents ->
            for (document in documents){
                val u = document.get("username")
                val p = document.get("password")
                if (u == username && p == hashString(password, "MD5")){
                    callback(true, u.toString())
                    loggedInUserId = document.id
                    loggedInUsername = u.toString()
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

    fun isUserLoggedIn(): Boolean {
        return loggedInUserId != null
    }

    fun getLoggedInUserId(): String? {
        return loggedInUserId
    }
}