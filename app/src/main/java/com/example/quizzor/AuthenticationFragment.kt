package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var llAuthenticationButtons: LinearLayout
    private lateinit var llRegisterForm: LinearLayout
    private lateinit var llLoginForm: LinearLayout
    private lateinit var imgBtnRegister: ImageButton
    private lateinit var imgBtnLogin: ImageButton
    private lateinit var buttonRegisterBack: Button
    private lateinit var buttonLoginBack: Button
    private lateinit var buttonRegister: Button
    private lateinit var buttonLogin: Button
    private lateinit var editTextLoginUsername: EditText
    private lateinit var editTextLoginPassword: EditText
    private lateinit var textViewLoginErrorMsg: TextView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var editTextRegisterNickname: EditText
    private lateinit var editTextRegisterUsername: EditText
    private lateinit var editTextRegisterPassword: EditText
    private lateinit var editTextRegisterConfirmPassword: EditText
    private lateinit var textViewRegisterErrorMsg: TextView
    private lateinit var textViewRegisterTitle: TextView
    private lateinit var textViewLoginTitle: TextView
    private lateinit var language: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_authentication, container, false)
        (activity as MainActivity).switchToGeneralMusic()
        val activity = activity as? MainActivity
        val userManager: UserManagement = activity?.getUserManagement() ?: throw IllegalStateException("MainActivity expected")
        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        language = getDataFromSharedPreferences("language")
        bottomNavigationView = activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        llAuthenticationButtons = view.findViewById<LinearLayout>(R.id.llAuthenticationButtons)
        llRegisterForm = view.findViewById<LinearLayout>(R.id.llRegisterForm)
        llLoginForm = view.findViewById<LinearLayout>(R.id.llLoginForm)
        imgBtnRegister = view.findViewById<ImageButton>(R.id.imgBtnRegister)
        imgBtnLogin = view.findViewById<ImageButton>(R.id.imgBtnLogin)
        buttonRegisterBack = view.findViewById<Button>(R.id.buttonRegisterBack)
        buttonLoginBack = view.findViewById<Button>(R.id.buttonLoginBack)
        buttonRegister = view.findViewById<Button>(R.id.buttonRegister)
        buttonLogin = view.findViewById<Button>(R.id.buttonLogin)
        textViewLoginErrorMsg = view.findViewById<TextView>(R.id.textViewLoginErrorMsg)
        editTextLoginUsername = view.findViewById<EditText>(R.id.editTextLoginUsername)
        editTextLoginPassword = view.findViewById<EditText>(R.id.editTextLoginPassword)
        editTextRegisterNickname = view.findViewById<EditText>(R.id.editTextRegisterNickname)
        editTextRegisterUsername = view.findViewById<EditText>(R.id.editTextRegisterUsername)
        editTextRegisterPassword = view.findViewById<EditText>(R.id.editTextRegisterPassword)
        editTextRegisterConfirmPassword = view.findViewById<EditText>(R.id.editTextRegisterConfirmPassword)
        textViewRegisterErrorMsg = view.findViewById<TextView>(R.id.textViewRegisterErrorMsg)
        textViewRegisterTitle = view.findViewById<TextView>(R.id.textViewRegisterTitle)
        textViewLoginTitle = view.findViewById<TextView>(R.id.textViewLoginTitle)

        loadUIText(language)

        imgBtnRegister.setOnClickListener{
            (activity as MainActivity).playButtonClickSound()
            showRegisterForm()
        }

        imgBtnLogin.setOnClickListener{
            (activity as MainActivity).playButtonClickSound()
            showLoginForm()
        }

        buttonRegisterBack.setOnClickListener{
            (activity as MainActivity).playButtonClickSound()
            hideRegisterForm()
        }

        buttonLoginBack.setOnClickListener {
            (activity as MainActivity).playButtonClickSound()
            hideLoginForm()
        }

        buttonLogin.setOnClickListener {
            (activity as MainActivity).playButtonClickSound()
            if (editTextLoginUsername.text.toString() != "" && editTextLoginPassword.text.toString() != ""){
                userManager.signIn(editTextLoginUsername.text.toString(), editTextLoginPassword.text.toString()) { callback, test ->
                    if (test != null) {
                        loginSuccessfully()
                    } else{
                        loginFailed()
                    }
                }
            } else {
                emptyFieldsWarning()
            }
        }

        buttonRegister.setOnClickListener {
            (activity as MainActivity).playButtonClickSound()
            if (registerFormFilled()){
                if(registerPasswordCheck()){
                    registerUser(userManager, editTextRegisterNickname.text.toString(), editTextRegisterUsername.text.toString(),editTextRegisterPassword.text.toString())
                }
            }
        }

        return view
    }

    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }
    private fun showRegisterForm(){
        llAuthenticationButtons.visibility = View.GONE
        llRegisterForm.visibility = View.VISIBLE
    }

    private fun showLoginForm(){
        llAuthenticationButtons.visibility = View.GONE
        llLoginForm.visibility = View.VISIBLE
    }

    private fun hideRegisterForm(){
        llAuthenticationButtons.visibility = View.VISIBLE
        llRegisterForm.visibility = View.GONE
        textViewRegisterErrorMsg.visibility = View.GONE
        editTextRegisterNickname.text.clear()
        editTextRegisterUsername.text.clear()
        editTextRegisterPassword.text.clear()
        editTextRegisterConfirmPassword.text.clear()
    }

    private fun hideLoginForm(){
        llAuthenticationButtons.visibility = View.VISIBLE
        llLoginForm.visibility = View.GONE
        textViewLoginErrorMsg.visibility = View.GONE
        editTextLoginUsername.text.clear()
        editTextLoginPassword.text.clear()
    }

    private fun hideRegisterButtons(){
        llAuthenticationButtons.visibility = View.GONE
    }

    private fun loginSuccessfully(){
        hideLoginForm()
        hideRegisterButtons()

        bottomNavigationView.visibility = View.VISIBLE
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun loginFailed(){
        textViewLoginErrorMsg.text = translatedErrorMessage(language, "IncorrectCredentials")
            textViewLoginErrorMsg.visibility = View.VISIBLE
    }

    private fun emptyFieldsWarning(){
        textViewLoginErrorMsg.text = translatedErrorMessage(language, "FormNotFilled")
        textViewLoginErrorMsg.visibility = View.VISIBLE
    }

    private fun registerFormFilled(): Boolean{
        if (editTextRegisterNickname.text.toString() != ""
            && editTextRegisterUsername.text.toString() != ""
            && editTextRegisterPassword.text.toString() != ""
            && editTextRegisterConfirmPassword.text.toString() != ""){
            return true
        }
        textViewRegisterErrorMsg.text = translatedErrorMessage(language, "FormNotFilled")
        textViewRegisterErrorMsg.visibility = View.VISIBLE
        return false
    }

    private fun registerPasswordCheck(): Boolean{
        if (editTextRegisterPassword.text.toString() == editTextRegisterConfirmPassword.text.toString()){
            return true
        }
        textViewRegisterErrorMsg.text = translatedErrorMessage(language, "DifferentPassword")
        textViewRegisterErrorMsg.visibility = View.VISIBLE
        return false
    }

    private fun registerUserError(type: String){
        if (type == "nickname"){
            textViewRegisterErrorMsg.text = translatedErrorMessage(language, "NicknameCheck")
            textViewRegisterErrorMsg.visibility = View.VISIBLE
        }else if (type == "user"){
            textViewRegisterErrorMsg.text = translatedErrorMessage(language, "UsernameCheck")
            textViewRegisterErrorMsg.visibility = View.VISIBLE
        }

    }

    private fun registerUser(userManager: UserManagement, nickname: String, user: String, password: String){
        CoroutineScope(Dispatchers.Main).launch {
            var registerCompleted: Char = userManager.registerUser(nickname, user, password)
            handleRegisterResult(registerCompleted)
        }
    }

    private fun handleRegisterResult(result: Char){
        when(result){
            '1' -> resetFragmentView()
            'n' -> registerUserError("nickname")
            'u' -> registerUserError("user")
        }
    }

    private fun resetFragmentView(){
        textViewRegisterErrorMsg.visibility = View.GONE
        llRegisterForm.visibility = View.GONE
        llAuthenticationButtons.visibility = View.VISIBLE
    }

    private fun loadUIText(language: String){
        when (language){
            "en" -> {
                textViewRegisterTitle.text = "Register"
                editTextRegisterNickname.hint = "Nickname"
                editTextRegisterUsername.hint = "Username"
                editTextRegisterPassword.hint = "Password"
                editTextRegisterConfirmPassword.hint = "Confirm Password"
                buttonRegisterBack.text = "Back"
                buttonRegister.text = "Register"

                textViewLoginTitle.text = "Login"
                editTextLoginUsername.hint = "Username"
                editTextLoginPassword.hint = "Password"
                buttonLogin.text = "Login"
                buttonLoginBack.text = "Back"
            }
            "ro" -> {
                textViewRegisterTitle.text = "Înregistrare"
                editTextRegisterNickname.hint = "Pseudonim"
                editTextRegisterUsername.hint = "Nume de utilizator"
                editTextRegisterPassword.hint = "Parolă"
                editTextRegisterConfirmPassword.hint = "Confirmă parola"
                buttonRegisterBack.text = "Înapoi"
                buttonRegister.text = "Înregistrare"

                textViewLoginTitle.text = "Autentificare"
                editTextLoginUsername.hint = "Nume de utilizator"
                editTextLoginPassword.hint = "Parolă"
                buttonLogin.text = "Autentificare"
                buttonLoginBack.text = "Înapoi"
            }
            "de" -> {
                textViewRegisterTitle.text = "Registrieren"
                editTextRegisterNickname.hint = "Spitzname"
                editTextRegisterUsername.hint = "Benutzername"
                editTextRegisterPassword.hint = "Passwort"
                editTextRegisterConfirmPassword.hint = "Passwort bestätigen"
                buttonRegisterBack.text = "Zurück"
                buttonRegister.text = "Registrieren"

                textViewLoginTitle.text = "Anmelden"
                editTextLoginUsername.hint = "Benutzername"
                editTextLoginPassword.hint = "Passwort"
                buttonLogin.text = "Anmelden"
                buttonLoginBack.text = "Zurück"
            }
            "fr" -> {
                textViewRegisterTitle.text = "S'inscrire"
                editTextRegisterNickname.hint = "Pseudo"
                editTextRegisterUsername.hint = "Nom d'utilisateur"
                editTextRegisterPassword.hint = "Mot de passe"
                editTextRegisterConfirmPassword.hint = "Confirmer le mot de passe"
                buttonRegisterBack.text = "Retour"
                buttonRegister.text = "S'inscrire"

                textViewLoginTitle.text = "Connexion"
                editTextLoginUsername.hint = "Nom d'utilisateur"
                editTextLoginPassword.hint = "Mot de passe"
                buttonLogin.text = "Connexion"
                buttonLoginBack.text = "Retour"
            }
            "hu" -> {
                textViewRegisterTitle.text = "Regisztráció"
                editTextRegisterNickname.hint = "Becenév"
                editTextRegisterUsername.hint = "Felhasználónév"
                editTextRegisterPassword.hint = "Jelszó"
                editTextRegisterConfirmPassword.hint = "Jelszó megerősítése"
                buttonRegisterBack.text = "Vissza"
                buttonRegister.text = "Regisztráció"

                textViewLoginTitle.text = "Bejelentkezés"
                editTextLoginUsername.hint = "Felhasználónév"
                editTextLoginPassword.hint = "Jelszó"
                buttonLogin.text = "Bejelentkezés"
                buttonLoginBack.text = "Vissza"
            }
            "jp" -> {
                textViewRegisterTitle.text = "登録"
                editTextRegisterNickname.hint = "ニックネーム"
                editTextRegisterUsername.hint = "ユーザー名"
                editTextRegisterPassword.hint = "パスワード"
                editTextRegisterConfirmPassword.hint = "パスワードを確認"
                buttonRegisterBack.text = "戻る"
                buttonRegister.text = "登録"

                textViewLoginTitle.text = "ログイン"
                editTextLoginUsername.hint = "ユーザー名"
                editTextLoginPassword.hint = "パスワード"
                buttonLogin.text = "ログイン"
                buttonLoginBack.text = "戻る"
            }
        }
    }

    private fun translatedErrorMessage(language: String, error: String): String{
        var errorMsg: String = ""
        when(language){
            "en" -> {
                when(error){
                    "IncorrectCredentials" -> {
                        errorMsg = "Incorrect Credentials!"
                    }
                    "FormNotFilled" -> {
                        errorMsg = "Please complete all form fields!"
                    }
                    "DifferentPassword" -> {
                        errorMsg = "Password fields are different!"
                    }
                    "NicknameCheck" -> {
                        errorMsg = "Nickname already exists! Choose another one!"
                    }
                    "UsernameCheck" -> {
                        errorMsg = "Username already exists! Choose another one!"
                    }
                }
            }
            "ro" -> {
                when(error){
                    "IncorrectCredentials" -> {
                        errorMsg = "Credintiale incorecte!"
                    }
                    "FormNotFilled" -> {
                        errorMsg = "Vă rugăm să completați toate câmpurile formularului!"
                    }
                    "DifferentPassword" -> {
                        errorMsg = "Câmpurile pentru parolă sunt diferite!"
                    }
                    "NicknameCheck" -> {
                        errorMsg = "Pseudonimul există deja! Alegeți altul!"
                    }
                    "UsernameCheck" -> {
                        errorMsg = "Numele de utilizator există deja! Alegeți altul!"
                    }
                }
            }
            "de" -> {
                when(error){
                    "IncorrectCredentials" -> {
                        errorMsg = "Falsche Anmeldeinformationen!"
                    }
                    "FormNotFilled" -> {
                        errorMsg = "Bitte füllen Sie alle Formularfelder aus!"
                    }
                    "DifferentPassword" -> {
                        errorMsg = "Passwortfelder sind unterschiedlich!"
                    }
                    "NicknameCheck" -> {
                        errorMsg = "Spitzname existiert bereits! Wählen Sie einen anderen!"
                    }
                    "UsernameCheck" -> {
                        errorMsg = "Benutzername existiert bereits! Wählen Sie einen anderen!"
                    }
                }
            }
            "fr" -> {
                when(error){
                    "IncorrectCredentials" -> {
                        errorMsg = "Identifiants incorrects!"
                    }
                    "FormNotFilled" -> {
                        errorMsg = "Veuillez compléter tous les champs du formulaire!"
                    }
                    "DifferentPassword" -> {
                        errorMsg = "Les champs de mot de passe sont différents!"
                    }
                    "NicknameCheck" -> {
                        errorMsg = "Le pseudo existe déjà! Choisissez-en un autre!"
                    }
                    "UsernameCheck" -> {
                        errorMsg = "Le nom d'utilisateur existe déjà! Choisissez-en un autre!"
                    }
                }
            }
            "hu" -> {
                when(error){
                    "IncorrectCredentials" -> {
                        errorMsg = "Helytelen adatok!"
                    }
                    "FormNotFilled" -> {
                        errorMsg = "Kérjük, töltse ki az összes űrlapmezőt!"
                    }
                    "DifferentPassword" -> {
                        errorMsg = "A jelszavak nem egyeznek!"
                    }
                    "NicknameCheck" -> {
                        errorMsg = "A becenév már létezik! Válasszon másikat!"
                    }
                    "UsernameCheck" -> {
                        errorMsg = "A felhasználónév már létezik! Válasszon másikat!"
                    }
                }
            }
            "jp" -> {
                when(error){
                    "IncorrectCredentials" -> {
                        errorMsg = "認証情報が正しくありません!"
                    }
                    "FormNotFilled" -> {
                        errorMsg = "すべてのフォームフィールドに記入してください!"
                    }
                    "DifferentPassword" -> {
                        errorMsg = "パスワードフィールドが異なります!"
                    }
                    "NicknameCheck" -> {
                        errorMsg = "ニックネームは既に存在します! 別のものを選んでください!"
                    }
                    "UsernameCheck" -> {
                        errorMsg = "ユーザー名は既に存在します! 別のものを選んでください!"
                    }
                }
            }
        }
        return errorMsg
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AuthenticationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}