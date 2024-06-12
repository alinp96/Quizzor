package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tvTitle: TextView
    private lateinit var backButton: Button
    private lateinit var userManager: UserManagement

    private lateinit var llProfileMainView: LinearLayout
    private lateinit var tvPrincipalText: TextView
    private lateinit var tvSecondaryText: TextView
    private lateinit var btnChangeNickname: Button
    private lateinit var btnChangePassword: Button
    private lateinit var btnResetScore: Button

    private lateinit var llChangeNickname: LinearLayout
    private lateinit var tvCurrentNickname: TextView
    private lateinit var etChangeNickname: EditText
    private lateinit var btnResetNickname: Button

    private lateinit var llChangePassword: LinearLayout
    private lateinit var tvPasswordText: TextView
    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmNewPassword: EditText
    private lateinit var btnResetPassword: Button
    private lateinit var tvErrorNickname: TextView
    private lateinit var tvErrorPassword: TextView

    private lateinit var oldNickname: String

    private lateinit var language: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val activity = activity as? MainActivity
        userManager = activity?.getUserManagement() ?: throw IllegalStateException("MainActivity expected")
        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        language = getDataFromSharedPreferences("language")

        oldNickname = userManager.getLoggedInNickname().toString()

        llProfileMainView = view.findViewById<LinearLayout>(R.id.llProfileMainView)
        tvPrincipalText = view.findViewById<TextView>(R.id.tvPrincipalText)
        tvSecondaryText = view.findViewById<TextView>(R.id.tvSecondaryText)
        tvTitle = view.findViewById<TextView>(R.id.titleTextView)
        btnChangeNickname = view.findViewById<Button>(R.id.btnChangeNickname)
        btnChangePassword = view.findViewById<Button>(R.id.btnChangePassword)
        btnResetScore = view.findViewById<Button>(R.id.btnResetScore)
        llChangeNickname = view.findViewById<LinearLayout>(R.id.llChangeNickname)
        tvCurrentNickname = view.findViewById<TextView>(R.id.tvCurrentNickname)
        etChangeNickname = view.findViewById<EditText>(R.id.etChangeNickname)
        btnResetNickname = view.findViewById<Button>(R.id.btnResetNickname)
        llChangePassword = view.findViewById<LinearLayout>(R.id.llChangePassword)
        tvPasswordText = view.findViewById<TextView>(R.id.tvPasswordText)
        etCurrentPassword = view.findViewById<EditText>(R.id.etCurrentPassword)
        etNewPassword = view.findViewById<EditText>(R.id.etNewPassword)
        etConfirmNewPassword = view.findViewById<EditText>(R.id.etConfirmNewPassword)
        btnResetPassword = view.findViewById<Button>(R.id.btnResetPassword)
        tvErrorNickname = view.findViewById<TextView>(R.id.tvErrorNickname)
        tvErrorPassword = view.findViewById<TextView>(R.id.tvErrorPassword)
        backButton = view.findViewById<Button>(R.id.backButton)

        loadUIText(language)
        setScore()
        setNickname()


        /*backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }*/

        backButton.setOnClickListener {
            showMainProfilePage()
        }

        btnChangeNickname.setOnClickListener {
            showChangeNicknameForm()
        }

        btnChangePassword.setOnClickListener {
            showChangePasswordForm()
        }

        btnResetScore.setOnClickListener {
            resetScore()
        }

        btnResetNickname.setOnClickListener {
            resetNickname()
        }

        btnResetPassword.setOnClickListener{
            resetPassword()
        }

        return view
    }

    private fun showMainProfilePage(){
        llProfileMainView.visibility = View.VISIBLE
        llChangeNickname.visibility = View.GONE
        llChangePassword.visibility = View.GONE
        etNewPassword.text.clear()
        etConfirmNewPassword.text.clear()
        etCurrentPassword.text.clear()
        etChangeNickname.text.clear()
        backButton.visibility = View.GONE
    }

    private fun showChangeNicknameForm(){
        llProfileMainView.visibility = View.GONE
        llChangeNickname.visibility = View.VISIBLE
        llChangePassword.visibility = View.GONE
        backButton.visibility = View.VISIBLE
    }

    private fun showChangePasswordForm(){
        llProfileMainView.visibility = View.GONE
        llChangeNickname.visibility = View.GONE
        llChangePassword.visibility = View.VISIBLE
        backButton.visibility = View.VISIBLE
    }

    private fun resetScore(){
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val currentScoreMap = documentSnapshot.get("score") as? Map<String, Any> ?: emptyMap()
            val updatedScoreMap = currentScoreMap.toMutableMap()
            for (category in currentScoreMap.keys) {
                updatedScoreMap[category] = 0
            }
            docRef.update("score", updatedScoreMap)
            setScore()
        }
    }

    private fun resetNickname(){

        val newNickname: String = etChangeNickname.text.toString()
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)

        if (newNickname != "") {
            if (oldNickname != newNickname) {
                tvErrorNickname.visibility = View.GONE
                docRef.update("nickname", newNickname)
                    .addOnSuccessListener {
                        setNickname()
                        showMainProfilePage()
                        etChangeNickname.text.clear()
                        // Update UI here after successful update
                    }
                    .addOnFailureListener { e ->
                        tvErrorNickname.text = "Error updating nickname: ${e.message}"
                        tvErrorNickname.visibility = View.VISIBLE
                    }
            } else {
                tvErrorNickname.text = translatedErrorMessage(language, "SameNick", "")
                tvErrorNickname.visibility = View.VISIBLE
            }
        } else {
            tvErrorNickname.text = translatedErrorMessage(language, "EmptyField", "")
            tvErrorNickname.visibility = View.VISIBLE
        }
    }

    private fun setNickname(){
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val nickname = documentSnapshot.get("nickname")?.toString() ?: ""
            tvCurrentNickname.text = translatedErrorMessage(language, "CurrentNickname", nickname)
            tvPrincipalText.text = translatedErrorMessage(language, "PrincipalText", nickname)
            oldNickname = nickname
        }.addOnFailureListener { e ->
            // Handle potential errors (e.g., document not found)
            println("Error getting nickname: $e")
        }

    }
    private fun resetPassword(){
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        var verifyCurrentPassword: String = etCurrentPassword.text.toString()
        var newPassword: String = etNewPassword.text.toString()
        var confirmNewPassword: String = etConfirmNewPassword.text.toString()

        if (verifyCurrentPassword != "" && newPassword != "" && confirmNewPassword != ""){
            newPassword = userManager.hashString(etNewPassword.text.toString(), "MD5")
            confirmNewPassword = userManager.hashString(etConfirmNewPassword.text.toString(), "MD5")
            verifyCurrentPassword = userManager.hashString(etCurrentPassword.text.toString(), "MD5")
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val currentPassword = documentSnapshot.get("password")?.toString() ?: ""
                if (currentPassword == verifyCurrentPassword){
                    if (newPassword != currentPassword){
                        if (newPassword == confirmNewPassword){
                            docRef.update("password", newPassword)
                            tvErrorPassword.visibility = View.GONE
                            showMainProfilePage()
                            etNewPassword.text.clear()
                            etConfirmNewPassword.text.clear()
                            etCurrentPassword.text.clear()
                        }else{
                            tvErrorPassword.text = translatedErrorMessage(language, "PasswordMatch", "")
                            tvErrorPassword.visibility = View.VISIBLE
                        }
                    }else{
                        tvErrorPassword.text = translatedErrorMessage(language, "SamePassword", "")
                        tvErrorPassword.visibility = View.VISIBLE
                    }
                }else{
                    tvErrorPassword.text = translatedErrorMessage(language, "WrongPassword", "")
                    tvErrorPassword.visibility = View.VISIBLE
                }

            }.addOnFailureListener { e ->
                // Handle potential errors (e.g., document not found)
                println("Error getting nickname: $e")
            }
        }else{
            tvErrorPassword.text = translatedErrorMessage(language, "EmptyField", "")
            tvErrorPassword.visibility = View.VISIBLE
        }
    }

    private fun setScore(){
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val currentScoreMap = documentSnapshot.get("score") as? Map<String, Int> ?: emptyMap()
            val totalScore = (currentScoreMap["total"] as? Number)?.toInt() ?: 0
            tvSecondaryText.text = translatedErrorMessage(language, "YourScore", totalScore.toString())
        }
    }

    private fun loadUIText(language: String){
        when (language){
            "en" -> {
                tvTitle.text = "Profile"
                backButton.text = "Back"
                btnChangeNickname.text = "Change Nickname"
                btnChangePassword.text = "Change Password"
                btnResetScore.text = "Reset Score"
                etChangeNickname.hint = "Change Nickname"
                btnResetNickname.text = "Reset Nickname"
                tvPasswordText.text = "Change your current password"
                etCurrentPassword.hint = "Current Password"
                etNewPassword.hint = "New Password"
                etConfirmNewPassword.hint = "Confirm Password"
                btnResetPassword.text = "Reset Password"
                btnResetScore.text = "Reset Score"
            }
            "ro" -> {
                tvTitle.text = "Profil"
                backButton.text = "Back"
                btnChangeNickname.text = "Schimba Porecla"
                btnChangePassword.text = "Schimba Parola"
                btnResetScore.text = "Reseteaza Scorul"
                etChangeNickname.hint = "Porecla noua"
                btnResetNickname.text = "Reseteaza Porecla"
                tvPasswordText.text = "Schimba parola"
                etCurrentPassword.hint = "Parola actuala"
                etNewPassword.hint = "Parola noua"
                etConfirmNewPassword.hint = "Confirma parola"
                btnResetPassword.text = "Reseteaza Parola"
                btnResetScore.text = "Reseteaza Scorul"
            }
            "de" -> {
                tvTitle.text = "Profil"
                backButton.text = "Zurück"
                btnChangeNickname.text = "Spitznamen ändern"
                btnChangePassword.text = "Passwort ändern"
                btnResetScore.text = "Punkte zurücksetzen"
                etChangeNickname.hint = "Spitznamen ändern"
                btnResetNickname.text = "Spitznamen zurücksetzen"
                tvPasswordText.text = "Ändern Sie Ihr aktuelles Passwort"
                etCurrentPassword.hint = "Aktuelles Passwort"
                etNewPassword.hint = "Neues Passwort"
                etConfirmNewPassword.hint = "Passwort bestätigen"
                btnResetPassword.text = "Passwort zurücksetzen"
                btnResetScore.text = "Punkte zurücksetzen"
            }
            "fr" -> {
                tvTitle.text = "Profil"
                backButton.text = "Retour"
                btnChangeNickname.text = "Changer le pseudonyme"
                btnChangePassword.text = "Changer le mot de passe"
                btnResetScore.text = "Réinitialiser le score"
                etChangeNickname.hint = "Changer le pseudonyme"
                btnResetNickname.text = "Réinitialiser le pseudonyme"
                tvPasswordText.text = "Changez votre mot de passe actuel"
                etCurrentPassword.hint = "Mot de passe actuel"
                etNewPassword.hint = "Nouveau mot de passe"
                etConfirmNewPassword.hint = "Confirmer le mot de passe"
                btnResetPassword.text = "Réinitialiser le mot de passe"
                btnResetScore.text = "Réinitialiser le score"
            }
            "hu" -> {
                tvTitle.text = "Profil"
                backButton.text = "Vissza"
                btnChangeNickname.text = "Becenév megváltoztatása"
                btnChangePassword.text = "Jelszó megváltoztatása"
                btnResetScore.text = "Pontszám visszaállítása"
                etChangeNickname.hint = "Becenév megváltoztatása"
                btnResetNickname.text = "Becenév visszaállítása"
                tvPasswordText.text = "Jelszó módosítása"
                etCurrentPassword.hint = "Jelenlegi jelszó"
                etNewPassword.hint = "Új jelszó"
                etConfirmNewPassword.hint = "Jelszó megerősítése"
                btnResetPassword.text = "Jelszó visszaállítása"
                btnResetScore.text = "Pontszám visszaállítása"
            }
            "jp" -> {
                tvTitle.text = "プロフィール"
                backButton.text = "戻る"
                btnChangeNickname.text = "ニックネームを変更"
                btnChangePassword.text = "パスワードを変更"
                btnResetScore.text = "スコアをリセット"
                etChangeNickname.hint = "ニックネームを変更"
                btnResetNickname.text = "ニックネームをリセット"
                tvPasswordText.text = "現在のパスワードを変更"
                etCurrentPassword.hint = "現在のパスワード"
                etNewPassword.hint = "新しいパスワード"
                etConfirmNewPassword.hint = "パスワードを確認"
                btnResetPassword.text = "パスワードをリセット"
                btnResetScore.text = "スコアをリセット"
            }

        }
    }

    private fun translatedErrorMessage(language: String, error: String, placeholder: String): String{
        var errorMsg: String = ""
        when(language){
            "en" -> {
                when(error){
                    "SameNick" -> {
                        errorMsg = "You have the same nickname!"
                    }
                    "EmptyField" -> {
                        errorMsg = "Empty fields not allowed!"
                    }
                    "PasswordMatch" -> {
                        errorMsg = "New password doesn't match!"
                    }
                    "SamePassword" -> {
                        errorMsg = "New password is the same with the current one!"
                    }
                    "WrongPassword" -> {
                        errorMsg = "Current password is incorrect!"
                    }
                    "CurrentNickname" -> {
                        errorMsg = "Your current nickname is ${placeholder}"
                    }
                    "PrincipalText" -> {
                        errorMsg = "Hello, ${placeholder}"
                    }
                    "YourScore" -> {
                        errorMsg = "Your Score: ${placeholder}"
                    }
                }
            }
            "ro" -> {
                when(error) {
                    "SameNick" -> {
                        errorMsg = "Ai aceeasi porecla!"
                    }

                    "EmptyField" -> {
                        errorMsg = "Completeaza toate campurile!"
                    }

                    "PasswordMatch" -> {
                        errorMsg = "Parolele nu sunt identice!"
                    }

                    "SamePassword" -> {
                        errorMsg = "Parola noua este identica cu cea precedenta!"
                    }

                    "WrongPassword" -> {
                        errorMsg = "Parola incorecta!"
                    }

                    "CurrentNickname" -> {
                        errorMsg = "Porecla ta actuala este ${placeholder}"
                    }

                    "PrincipalText" -> {
                        errorMsg = "Salut, ${placeholder}"
                    }

                    "YourScore" -> {
                        errorMsg = "Scorul Tau: ${placeholder}"
                    }
                }
            }
            "de" -> {
                when(error){
                    "SameNick" -> {
                        errorMsg = "Sie haben denselben Spitznamen!"
                    }
                    "EmptyField" -> {
                        errorMsg = "Leere Felder sind nicht erlaubt!"
                    }
                    "PasswordMatch" -> {
                        errorMsg = "Das neue Passwort stimmt nicht überein!"
                    }
                    "SamePassword" -> {
                        errorMsg = "Das neue Passwort ist dasselbe wie das aktuelle!"
                    }
                    "WrongPassword" -> {
                        errorMsg = "Das aktuelle Passwort ist falsch!"
                    }
                    "CurrentNickname" -> {
                        errorMsg = "Ihr aktueller Spitzname ist ${placeholder}"
                    }
                    "PrincipalText" -> {
                        errorMsg = "Hallo, ${placeholder}"
                    }
                    "YourScore" -> {
                        errorMsg = "Ihre Punktzahl: ${placeholder}"
                    }
                }
            }
            "fr" -> {
                when(error){
                    "SameNick" -> {
                        errorMsg = "Vous avez le même pseudonyme !"
                    }
                    "EmptyField" -> {
                        errorMsg = "Les champs vides ne sont pas autorisés !"
                    }
                    "PasswordMatch" -> {
                        errorMsg = "Le nouveau mot de passe ne correspond pas !"
                    }
                    "SamePassword" -> {
                        errorMsg = "Le nouveau mot de passe est identique à l'actuel !"
                    }
                    "WrongPassword" -> {
                        errorMsg = "Le mot de passe actuel est incorrect !"
                    }
                    "CurrentNickname" -> {
                        errorMsg = "Votre pseudonyme actuel est ${placeholder}"
                    }
                    "PrincipalText" -> {
                        errorMsg = "Bonjour, ${placeholder}"
                    }
                    "YourScore" -> {
                        errorMsg = "Votre score : ${placeholder}"
                    }
                }
            }
            "hu" -> {
                when(error){
                    "SameNick" -> {
                        errorMsg = "Ugyanaz a becenév!"
                    }
                    "EmptyField" -> {
                        errorMsg = "Üres mezők nem engedélyezettek!"
                    }
                    "PasswordMatch" -> {
                        errorMsg = "Az új jelszó nem egyezik!"
                    }
                    "SamePassword" -> {
                        errorMsg = "Az új jelszó megegyezik a jelenlegivel!"
                    }
                    "WrongPassword" -> {
                        errorMsg = "A jelenlegi jelszó helytelen!"
                    }
                    "CurrentNickname" -> {
                        errorMsg = "Jelenlegi beceneved ${placeholder}"
                    }
                    "PrincipalText" -> {
                        errorMsg = "Helló, ${placeholder}"
                    }
                    "YourScore" -> {
                        errorMsg = "Pontszámod: ${placeholder}"
                    }
                }
            }
            "jp" -> {
                when(error){
                    "SameNick" -> {
                        errorMsg = "同じニックネームがあります！"
                    }
                    "EmptyField" -> {
                        errorMsg = "空のフィールドは許可されていません！"
                    }
                    "PasswordMatch" -> {
                        errorMsg = "新しいパスワードが一致しません！"
                    }
                    "SamePassword" -> {
                        errorMsg = "新しいパスワードが現在のパスワードと同じです！"
                    }
                    "WrongPassword" -> {
                        errorMsg = "現在のパスワードが間違っています！"
                    }
                    "CurrentNickname" -> {
                        errorMsg = "あなたの現在のニックネームは ${placeholder} です"
                    }
                    "PrincipalText" -> {
                        errorMsg = "こんにちは、 ${placeholder}"
                    }
                    "YourScore" -> {
                        errorMsg = "あなたのスコア： ${placeholder}"
                    }
                }
            }
        }
        return errorMsg
    }

    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}